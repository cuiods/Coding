#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define TRUE 1
#define FALSE 0
#define BLUE "\e[0;34m"
#define RED "\e[0;31m"
#define NONE "\e[0m"
#define PATHNAME "abc.img"
#define MAXSIZE 0xf00
#pragma pack (1) /*指定按1字节对齐*/
//偏移11个字节
struct BPB {
    unsigned short  BPB_BytsPerSec;    //每扇区字节数
    unsigned char   BPB_SecPerClus;    //每簇扇区数
    unsigned short  BPB_RsvdSecCnt;    //Boot记录占用的扇区数
    unsigned char   BPB_NumFATs;   //FAT表个数
    unsigned short  BPB_RootEntCnt;    //根目录最大文件数
    unsigned short  BPB_TotSec16;
    unsigned char   BPB_Media;
    unsigned short  BPB_FATSz16;   //FAT扇区数
    unsigned short  BPB_SecPerTrk;
    unsigned short  BPB_NumHeads;
    unsigned int  BPB_HiddSec;
    unsigned int  BPB_TotSec32;  //如果BPB_FATSz16为0，该值为FAT扇区数
};
#pragma pack () /*取消指定对齐，恢复缺省对齐*/
struct FileInfo {
    char fileName[12];//file name
    int isFolder;//whether directory
    int fstClus;//first clus number
    int fileSize;//size of file
    char *fileContent;//content of file
};
struct TreeNode {
    struct FileInfo* fileinfo;//file info
    struct TreeNode *parent;//parent node
    struct TreeNode *next;//next child
    struct TreeNode *firstChild;//its own next child
};
//output:nasm
char* _printStr_;
int _length_;
void my_print();
void print(char*,char*);
//functions
int getNextValue(int num);//get next location
int endOfRoot(unsigned char buf[32]);//root section end
void toLowerCase(struct FileInfo* fileinfo);//to lower case
void getContent(struct FileInfo* fileinfo);//read content
int appendNode(int offset, struct TreeNode* parent);//read an entry as a tree node.
int buildTree();//read all the file info
void printNode(struct TreeNode* node);//show node
struct TreeNode* getNode(char folder[30][30], int index, int max, struct TreeNode* parent);//get right tree node.
void showNode(struct TreeNode* node);//print this node and children of this node.
void showCount(struct TreeNode* node, int offcount);
int cut(char dest[30][30], char src[100]);//split path
int isLuama(unsigned char buf[32]);//whether delete file
/**
* global
*/
FILE *file;//文件
struct TreeNode* root;//目录根节点
unsigned int BPB_BytsPerSec;//每扇区字节数
unsigned int BPB_SecPerClus;//每簇扇区数
unsigned int BPB_RsvdSecCnt;//Boot记录占用多少扇区
unsigned int BPB_NumFATs;//共有多少FAT表
unsigned int BPB_RootEntCnt;//根目录文件数最大值
unsigned int BPB_TotSec16;//扇区总数
unsigned int BPB_FATSz16;//每FAT扇区数

int main() {
    buildTree();
    showNode(root);
    while (TRUE){
        int num;
        char read[100];
        char folder[30][30];
        print("Please enter your path：(0 to exit)",NONE);
        scanf("%s",read);
        if (strcmp(read, "0") == 0)
            break;
        if (strcmp(read, "count") == 0) {
            scanf("%s", read);
            num = cut(folder, read);
            struct TreeNode* node = getNode(folder, 0, num, root);
            if (node == NULL) {
                print("Unknown file\n", RED);
            } else {
                showCount(node,0);
            }
        }else{
            num = cut(folder, read);
            struct TreeNode* node = getNode(folder, 0, num, root);
            if (node == NULL) {
                print("Unknown file\n",RED);
            } else if (!node->fileinfo->isFolder) {
                print(node->fileinfo->fileContent,NONE);
            } else {
                showNode(node);
            }
        }
    }
}
/**
* get next fat value by current clus number
*/
int  getNextValue(int num) {
    //FAT1的偏移字节
    int fatBase = BPB_RsvdSecCnt * BPB_BytsPerSec;
    //FAT项的偏移字节
    int fatPos = fatBase + num*3/2;
    //奇偶FAT项处理方式不同，分类进行处理，从0号FAT项开始
    int type = 0;
    if (num % 2 == 0) {
        type = 0;
    } else {
        type = 1;
    }
    //先读出FAT项所在的两个字节
    unsigned short bytes;
    unsigned short* bytes_ptr = &bytes;
    int currentOff=ftell(file);
    fseek(file,fatPos,SEEK_SET);
    fread(bytes_ptr,2,1,file);
    fseek(file,currentOff,SEEK_SET);
    //u16为short，结合存储的小尾顺序和FAT项结构可以得到
    //type为0的话，取byte2的低4位和byte1构成的值，type为1的话，取byte2和byte1的高4位构成的值
    if (type == 0) {
        return bytes&0x0fff;
    }
    else return bytes>>4;
}

/**
* print a string
*/
void print(char* str,char* color){
    _printStr_ = color;
    _length_ = strlen(color);
    my_print();
    _printStr_ = str;
    _length_ = strlen(_printStr_);
    my_print();
    _printStr_ = NONE;
    _length_ = strlen(NONE);
    my_print();
}

void showNode(struct TreeNode* node) {
    if (node == NULL) {
        return;
    }
    struct TreeNode* childnode = node->firstChild;
    if (childnode == NULL) {//already leaf node
        printNode(node);
        print("\n",NONE);
    } else {
        while (childnode != NULL) {//has more child
            showNode(childnode);
            childnode = childnode->next;
        }
    }
}
/**
*show directory count
*/
void showCount(struct TreeNode* node, int offcount) {
    if(node->firstChild==NULL) {
        if (node->fileinfo->isFolder) {
            //print space
            int i = 0;
            for (i = 0; i < offcount; ++i) {
                print("    ",NONE);
            }
            printf("%s: 0 file, 0 directory\n", node->fileinfo->fileName);
            return;
        } else {
            printf("This is not a directory!\n");
            return;
        }
    }
    //print space
    int i = 0;
    for (i = 0; i < offcount; ++i) {
        print("    ",NONE);
    }
    //count folder and file
    int folder = 0;
    int file = 0;
    if (node->firstChild!=NULL) {
        struct TreeNode* p = node->firstChild;
        while(p) {
            if (p->fileinfo->isFolder) folder++;
            else file++;
            p = p->next;
        }
        folder = getFolderNum(node->firstChild);
        file = getFileNum(node->firstChild);
        printf("%s: ", node->fileinfo->fileName);
        if(file > 1) printf("%d files, ", file);
        else printf("%d file, ", file);
        if(folder > 1) printf("%d directories\n",folder);
        else printf("%d directory\n", folder);
        p = node -> firstChild;
        while(p) {
            if (p->fileinfo->isFolder) {
                showCount(p, offcount+1);
            }
            p = p->next;
        }
    }
}

int getFolderNum(struct TreeNode* node){
    if(node == NULL) return 0;
    int sum = 0;
    struct TreeNode* p = node;
    while(p) {
        if(p->fileinfo->isFolder) sum++;
        p = p->next;
    }
    p = node;
    while(p) {
        if (p -> fileinfo ->isFolder) sum+=getFolderNum(p->firstChild);
        p = p->next;
    }
    return sum;
}

int getFileNum(struct TreeNode* node) {
    if(node == NULL) return 0;
    int sum = 0;
    struct TreeNode* p = node;
    while(p) {
        if(!p->fileinfo->isFolder) sum++;
        p = p->next;
    }
    p = node;
    while(p) {
        if (p->fileinfo->isFolder) sum+=getFileNum(p->firstChild);
        p = p->next;
    }
    return sum;
}

void printNode(struct TreeNode* node) {
    if (node == root) {
        return;
    }
    printNode(node->parent);
    if (node->fileinfo->isFolder){//folder
        print(node->fileinfo->fileName, BLUE);
        print("/",NONE);
    }else{//file
        print(node->fileinfo->fileName, NONE);
    }
}
/**
* read file and build tree structure
*/
int buildTree() {
    file = fopen(PATHNAME, "rb");
    //boot section has 32 bit
    struct BPB bpb;
    struct BPB* bpb_ptr = &bpb;
    //BPB从偏移11个字节处开始
    fseek(file,11,SEEK_SET);
    fread(bpb_ptr,1,25,file);
    //init global
    BPB_BytsPerSec = bpb_ptr->BPB_BytsPerSec;
    BPB_SecPerClus = bpb_ptr->BPB_SecPerClus;//每簇扇区数
    BPB_RsvdSecCnt = bpb_ptr->BPB_RsvdSecCnt;//Boot记录占用多少扇区
    BPB_NumFATs = bpb_ptr->BPB_NumFATs;//共有多少FAT表
    BPB_RootEntCnt = bpb_ptr->BPB_RootEntCnt;//根目录文件数最大值
    BPB_TotSec16 = bpb_ptr->BPB_TotSec16;//扇区总数
    BPB_FATSz16 = bpb_ptr->BPB_FATSz16;
    //init tree
    root = malloc(sizeof(struct TreeNode));
    root->parent = NULL;
    root->next = NULL;
    root->firstChild = NULL;
    //read one piece to root
    int offset = 0x2600;
    while (TRUE) {
        offset = appendNode(offset, root);
        if (offset == 0)
            break;
    }
    fclose(file);
    return 1;
}
/**
* read one single entry and add it as a child to parent node.
*/
int appendNode(int offset, struct TreeNode* parent){
    fseek(file, offset, SEEK_SET);
    //a root entry has 32 bytes
    unsigned char rootEntry[32];
    fread(rootEntry, 32, 1, file);//read root entry
    offset = ftell(file);
    //if end, return 0
    if (endOfRoot(rootEntry)) return 0;
    struct FileInfo* fileinfo = malloc(sizeof(struct FileInfo));
    fileinfo->fileContent = NULL;
    //whether a folder or file
    if (rootEntry[0xB] == 0x10) fileinfo->isFolder = 1;
    else fileinfo->isFolder = 0;
    int index = 0;  //length of file name
    //if is a file
    if (!fileinfo->isFolder) {
        //read name
        for (index = 0; index < 8; index++) {
            if (rootEntry[index] == 0x20) break;
            fileinfo->fileName[index] = rootEntry[index];
        }
        fileinfo->fileName[index] = '.';
        index ++;
        //read expand name
        int i = 0;
        for (i = 8; i < 0xB; i++) {
            if (rootEntry[i] == 0x20) break;
            fileinfo->fileName[index] = rootEntry[i];
            index++;
        }
    } else {// if is a folder
        //read folder name
        for (index = 0; index < 0xB; index++) {
            if (rootEntry[index] == 0x20) break;
            fileinfo->fileName[index] = rootEntry[index];
        }
    }
    //create node
    fileinfo->fileName[index] = 0; //end of name str
    fileinfo->fstClus = rootEntry[0x1A] + rootEntry[0x1B] * 256;// 2 bytes 2^8
    fileinfo->fileSize = *((int*)&rootEntry[0x1C]);//file size
    if (!fileinfo->isFolder && !isLuama((unsigned char *)fileinfo)) {
        getContent(fileinfo);
    }
    if (isLuama((unsigned char *)fileinfo)) return offset;
    toLowerCase(fileinfo);//to lower case
    //add tree node
    struct TreeNode* node = malloc(sizeof(struct TreeNode));//create node
    node->fileinfo = fileinfo;//set file info
    node->parent = parent;//set parent
    node->next = NULL;//set next null
    node->firstChild = NULL;//set child null
    if (parent->firstChild == NULL) {//if parent is null
        parent->firstChild = node;
    }else{
        struct TreeNode* p = parent->firstChild;//get first child node.
        while (p->next != NULL) {
            p = p->next;
        }//to the end of the list
        p->next = node;
    }
    if (fileinfo->isFolder) {//if is folder
        int offset2 = 0x2600 + BPB_RootEntCnt * 0x20 + (fileinfo->fstClus - 2) * 0x200 + 0x40;
        while (TRUE) {
            offset2 = appendNode(offset2, node);
            if (offset2 == 0) break;
        }
    }
    return offset;
}
/**
* split the string by "/" and save strs in a 2 dimension array.
* return total number of path name.
*/
int cut(char dest[30][30], char src[100]) {
    int num = 0;
    char *str = strtok(src, "/");
    while (str) {
        strcpy(dest[num], str);
        num ++;
        str = strtok(NULL, "/");
    }
    return num;
}
/**
*get file node by path name.
*/
struct TreeNode* getNode(char folder[30][30], int index, int max, struct TreeNode* parent) {
    struct TreeNode* node = parent->firstChild;
    if (max == index)
        return parent;
    while (node) {
        if (strcmp(folder[index], node->fileinfo->fileName) == 0) {//This index has same name. try next one.
            return getNode(folder, index + 1, max, node);
        }
        node = node->next;
    }
    return NULL;
}
/**
*to lower case
*/
void toLowerCase(struct FileInfo* fileinfo) {
    int i;
    for (i = 0; i < 12; i ++) {
        char c = fileinfo->fileName[i];
        if (c == 0) break;
        if (0x41 <= c && c <= 0x5A)
            fileinfo->fileName[i] = c + 0x20;
    }
}
/**
* whether the end of root entry section.
* end if and only if all bits are zero
*/
int endOfRoot(unsigned char temp[32]) {
    int i = 0;
    for(;i < 32; i++) {
        if (temp[i]!=0) return FALSE;
    }
    return TRUE;
}
int isLuama(unsigned char buf[32]) {
    if (buf[0] == 0xE5) return TRUE;
    int i;
    for (i = 0; i < 0xB; i ++) {
        if (0x61 <= buf[i] && buf[i] <= 0x7A) return TRUE;
        if (buf[i] == 0x7E) return TRUE;
    }
    return FALSE;
}
/**
*get file content
*/
void getContent(struct FileInfo* fileinfo) {
    int currentOff=ftell(file);//save file offset
    if(fileinfo->fileSize > 0x200) fileinfo->fileContent = malloc(MAXSIZE);// create space for file content
    else fileinfo->fileContent = malloc(0x200);//one clus 512bytes
    if (fileinfo->fstClus == 0)return;
    int offset = 0x2600 + BPB_RootEntCnt * 0x20 + (fileinfo->fstClus - 2) * 0x200;//calculate offset.
    fseek(file, offset, SEEK_SET);
    fread(fileinfo->fileContent, 0x200, 1, file);
    int next_cnt;//file next clus(big file)
    if(fileinfo->fileSize > 0x200){//big file
        next_cnt=getNextValue(fileinfo->fstClus);
        while(next_cnt < 0xFF8){//loop FF8 is the last clus
            if(next_cnt==0xFF7) break;//bad
            int temp_off = 0x2600 + BPB_RootEntCnt * 0x20 + (next_cnt - 2) * 0x200;//data off
            fseek(file, temp_off, SEEK_SET);
            char* temp=malloc(0x200);//buffer 512 bytes
            fread(temp, 0x200, 1, file);
            strcat(fileinfo->fileContent,temp);
            next_cnt=getNextValue(next_cnt);
        }
    }
    fseek(file,currentOff,SEEK_SET);//return original offset
}
