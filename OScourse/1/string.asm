SECTION .text
global _start
_start:
call readchar ; Read a character into char
cmp byte [char], 0ah ; Have we reached the end of the line?
je check42 ; If so, check if 42 was entered
mov eax, buf ; Point eax at the start of the buffer
add ax, word [count] ; Add the current number of characters to eax
mov bx, [char] ; Copy the character to the bx register
mov [eax], bl ; Put the character to the end of the buffer
inc word [count] ; Increment the count (number of characters)
jmp _start ; Loop through
check42:
mov ax, word [buf] ; We're assuming the number is two digits or less
cmp ax, 3234h ; Is it 42?
je end ; If so, end
call writeln ; If not, write out the character
mov word [count], 0 ; Reset the count
jmp _start ; Loop
end:
mov eax, 01h ; sys_end
mov ebx, 00h ; Error number
int 80h ; Make sys call
readchar:
mov eax, 03h ; sys_read
mov ebx, 00h ; stdin
mov ecx, char ; Where to put the input
mov edx, 01h ; How many characters to read?
int 80h
ret
writeln:
mov eax, 04h ; sys_write
mov ebx, 01h ; stdout
mov ecx, buf ; Where to read from
mov edx, 0 ; Clear edx
mov dx, word [count] ; Copy count into the lower part of edx
int 80h
mov eax, 04h ; sys_write
mov ebx, 01h ; stdout
mov ecx, cr ; Carriage return
mov edx, 01h ; One character
int 80h
ret

SECTION .data
char db 0
count dw 0
cr db 0ah
SECTION .bss
buf resb 255