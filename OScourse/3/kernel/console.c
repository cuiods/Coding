
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			      console.c
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
						    Forrest Yu, 2005
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

/*
	回车键: 把光标移到第一列
	换行键: 把光标前进到下一行
*/


#include "type.h"
#include "const.h"
#include "protect.h"
#include "string.h"
#include "proc.h"
#include "tty.h"
#include "console.h"
#include "global.h"
#include "keyboard.h"
#include "proto.h"

PRIVATE void set_cursor(unsigned int position);
PRIVATE void set_video_start_addr(u32 addr);
PRIVATE void flush(CONSOLE* p_con);

extern unsigned int tabs[100];
extern int tabs_len;
extern int input_mode;
extern int mode_length;
extern int isTab;
extern int enter;
extern int deletemode;

int alignment = 0;
int notOut = 0;

/*======================================================================*
			   clear_screen
 *======================================================================*/
PUBLIC void clear_screen(CONSOLE * p_con)
{
 while (p_con -> cursor != 1 && input_mode == 0)
 {
   out_char(p_con,'\b');
 }
}
/*======================================================================*
			   init_screen
 *======================================================================*/
PUBLIC void init_screen(TTY* p_tty)
{
	notOut = 0;
	alignment = 0;
	int nr_tty = p_tty - tty_table;
	p_tty->p_console = console_table + nr_tty;

	int v_mem_size = V_MEM_SIZE >> 1;	/* 显存总大小 (in WORD) */

	int con_v_mem_size                   = v_mem_size / NR_CONSOLES;
	p_tty->p_console->original_addr      = nr_tty * con_v_mem_size;
	p_tty->p_console->v_mem_limit        = con_v_mem_size;
	p_tty->p_console->current_start_addr = p_tty->p_console->original_addr;

	/* 默认光标位置在最开始处 */
	p_tty->p_console->cursor = p_tty->p_console->original_addr;

	if (nr_tty == 0) {
		/* 第一个控制台沿用原来的光标位置 */
		p_tty->p_console->cursor = disp_pos / 2;
		disp_pos = 0;
	}
	else {
		out_char(p_tty->p_console, nr_tty + '0');
		out_char(p_tty->p_console, '#');
	}

	set_cursor(p_tty->p_console->cursor);
}


/*======================================================================*
			   is_current_console
*======================================================================*/
PUBLIC int is_current_console(CONSOLE* p_con)
{
	return (p_con == &console_table[nr_current_console]);
}


/*======================================================================*
			   out_char
 *======================================================================*/
PUBLIC void out_char(CONSOLE* p_con, char ch)
{
	u8* p_vmem = (u8*)(V_MEM_BASE + p_con->cursor * 2);
	int count = 0;
	switch(ch) {
	case ' ':
		if(notOut > 0) {
			notOut--;
			break;
		}
		if(isTab==0) {
			if (p_con->cursor <
		    	p_con->original_addr + p_con->v_mem_limit - 1) {
				char ch_color =  DEFAULT_CHAR_COLOR;
				switch(input_mode) {
					case 0:ch_color = DEFAULT_CHAR_COLOR;break;
					case 1:ch_color = MAKE_COLOR(BLACK, RED);mode_length++;break;
				}
				*p_vmem++ = ch;
				*p_vmem++ = ch_color;
				p_con->cursor++;
			}
		} else {
			// if(p_con->cursor%80==0) break;
			u8* lastLine = p_vmem - SCREEN_WIDTH * 2;
			if(isTab == 1 && *lastLine == ' ') alignment = 1;
			if(isTab > 1 && alignment == 1 && *lastLine != ' '){
				alignment = 0;
				notOut = 4 - isTab;
				break;
			}
			if (p_con->cursor <
		    	p_con->original_addr + p_con->v_mem_limit - 1) {
				char ch_color =  DEFAULT_CHAR_COLOR;
				switch(input_mode) {
					case 0:ch_color = DEFAULT_CHAR_COLOR;break;
					case 1:ch_color = MAKE_COLOR(BLACK, RED);mode_length++;break;
				}
				*p_vmem++ = ch;
				*p_vmem++ = ch_color;
				p_con->cursor++;
			}
			isTab = (isTab+1)%5;
			if (isTab==0 && lastLine >= V_MEM_BASE && *(lastLine+2)==' '&& *(lastLine+4)!=' ') {
				*p_vmem++ = ' ';
				*p_vmem++ = DEFAULT_CHAR_COLOR;
				p_con->cursor++;
			}
		}
		break;
	case '\n':
		if (p_con->cursor < p_con->original_addr +
		    p_con->v_mem_limit - SCREEN_WIDTH) {
			p_con->cursor = p_con->original_addr + SCREEN_WIDTH * 
				((p_con->cursor - p_con->original_addr) /
				 SCREEN_WIDTH + 1);
		}
		break;
	case '\b':
//		if (p_con->cursor > p_con->original_addr) {
            if (tabs_len > 0 && p_con->cursor == tabs[tabs_len-1]+4){ 
            	tabs_len--;
                p_con->cursor -= 4;
            } else  {
                p_con->cursor--;
                *(p_vmem-2) = ' ';
	            *(p_vmem-1) = DEFAULT_CHAR_COLOR;
                p_vmem = (u8*)(V_MEM_BASE + p_con->cursor * 2);
                   if ((p_con->cursor - p_con->original_addr+1) % SCREEN_WIDTH  == 0) {
                   	if(deletemode == 0) {
                   		p_con->cursor = enter;
                   	}else{
                   		while (*(p_vmem-2) == ' ' && p_con->cursor > 0)
                        {
                         p_con->cursor--;
                         p_vmem = (u8*)(V_MEM_BASE + p_con->cursor * 2);
                         if ((p_con->cursor - p_con->original_addr) % SCREEN_WIDTH  == 0) break;
                        }
                   	}
                   }
                        
                 }
//		   }

		break;
	default:
		if (p_con->cursor <
		    p_con->original_addr + p_con->v_mem_limit - 1 && input_mode!=2) {
			char ch_color =  DEFAULT_CHAR_COLOR;
			switch(input_mode) {
				case 0:ch_color = DEFAULT_CHAR_COLOR;break;
				case 1:ch_color = MAKE_COLOR(BLACK, RED);mode_length++;break;
			}
			*p_vmem++ = ch;
			*p_vmem++ = ch_color;
			p_con->cursor++;
		}
		break;
	}

	// while (p_con->cursor >= p_con->current_start_addr + SCREEN_SIZE) {
	// 	scroll_screen(p_con, SCR_DN);
	// }

	flush(p_con);
}

/*======================================================================*
                           flush
*======================================================================*/
PRIVATE void flush(CONSOLE* p_con)
{
        set_cursor(p_con->cursor);
        set_video_start_addr(p_con->current_start_addr);
}

/*======================================================================*
			    set_cursor
 *======================================================================*/
PRIVATE void set_cursor(unsigned int position)
{
	disable_int();
	out_byte(CRTC_ADDR_REG, CURSOR_H);
	out_byte(CRTC_DATA_REG, (position >> 8) & 0xFF);
	out_byte(CRTC_ADDR_REG, CURSOR_L);
	out_byte(CRTC_DATA_REG, position & 0xFF);
	enable_int();
}

/*======================================================================*
			  set_video_start_addr
 *======================================================================*/
PRIVATE void set_video_start_addr(u32 addr)
{
	disable_int();
	out_byte(CRTC_ADDR_REG, START_ADDR_H);
	out_byte(CRTC_DATA_REG, (addr >> 8) & 0xFF);
	out_byte(CRTC_ADDR_REG, START_ADDR_L);
	out_byte(CRTC_DATA_REG, addr & 0xFF);
	enable_int();
}



/*======================================================================*
			   select_console
 *======================================================================*/
PUBLIC void select_console(int nr_console)	/* 0 ~ (NR_CONSOLES - 1) */
{
	if ((nr_console < 0) || (nr_console >= NR_CONSOLES)) {
		return;
	}

	nr_current_console = nr_console;

	set_cursor(console_table[nr_console].cursor);
	set_video_start_addr(console_table[nr_console].current_start_addr);
}

/*======================================================================*
			   scroll_screen
 *----------------------------------------------------------------------*
 滚屏.
 *----------------------------------------------------------------------*
 direction:
	SCR_UP	: 向上滚屏
	SCR_DN	: 向下滚屏
	其它	: 不做处理
 *======================================================================*/
PUBLIC void scroll_screen(CONSOLE* p_con, int direction)
{
	if (direction == SCR_UP) {
		if (p_con->current_start_addr > p_con->original_addr) {
			p_con->current_start_addr -= SCREEN_WIDTH;
		}
	}
	else if (direction == SCR_DN) {
		if (p_con->current_start_addr + SCREEN_SIZE <
		    p_con->original_addr + p_con->v_mem_limit) {
			p_con->current_start_addr += SCREEN_WIDTH;
		}
	}
	else{
	}

	set_video_start_addr(p_con->current_start_addr);
	set_cursor(p_con->cursor);
}

/*======================================================================*
			   find_string
 *======================================================================*/
PUBLIC void find_string(CONSOLE* p_con) {
	u8* p_vmem = (u8*)(V_MEM_BASE + (p_con->cursor - mode_length) * 2);
	char find_dtr[mode_length];
	int i = 0;
	for (i = 0; i < mode_length; i++) {
		find_dtr[i] = *p_vmem++;
		p_vmem++;
	}
	p_vmem = (u8*)(V_MEM_BASE + (p_con->cursor - mode_length) * 2);
	u8* p_vmemstart = (u8*)(V_MEM_BASE);
	u8* index = p_vmemstart;
	int true = 0;
	for (; index < p_vmem; index++) {
		char temp = *index++;
		if(temp == find_dtr[true]){
			true++;
			if(true == mode_length) {
				//color
				int j = 0;
				for (; j < mode_length; j++) {
					*(index-(j*2)) = MAKE_COLOR(BLACK,BLUE);
				}
				true = 0;
			}
		}else{
			for(; true>0; true--){
				index = index - 2;
			}
			true = 0;
		}
	}
}
/*======================================================================*
			   find_string
 *======================================================================*/
 PUBLIC void cleanColor(CONSOLE* p_con){
 	u8* p_vmemstart = (u8*)(V_MEM_BASE);
 	u8* p_vmem = (u8*)(V_MEM_BASE + (p_con->cursor - mode_length) * 2);
 	for(p_vmemstart++; p_vmemstart <= p_vmem; p_vmemstart++) {
 		*(p_vmemstart++) = MAKE_COLOR(BLACK,WHITE);
 	}
 }