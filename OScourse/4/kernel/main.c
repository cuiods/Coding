
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            main.c
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                                    Forrest Yu, 2005
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

#include "type.h"
#include "const.h"
#include "protect.h"
#include "string.h"
#include "proc.h"
#include "tty.h"
#include "console.h"
#include "global.h"
#include "proto.h"
extern int to_clear;
int customerNum = 0;


PUBLIC void sys_tem_p(int unused1,int unused2,struct semaphore * s,struct proc * p) {
  s->value--;
  if (s->value<0) {
    s->list[s->len++] = p_proc_ready;
    p_proc_ready->p_flags = 1;
    schedule();
  }
}
PUBLIC void sys_tem_v(int unused1,int unused2,struct semaphore * s,struct proc * p) {
  s->value++;
  if (s->value<=0) {
    s->list[0]->p_flags = 0;
    int i = 1;
    for (i=1;i<=s->len;++i) {
      s->list[i-1] = s->list[i];
    }
    s->len--;
  }
}
/*======================================================================*
                            kernel_main
 *======================================================================*/
PUBLIC int kernel_main() {
	disp_str("-----\"kernel_main\" begins-----\n");

	struct task* p_task;
	struct proc* p_proc= proc_table;
	char* p_task_stack = task_stack + STACK_SIZE_TOTAL;
	u16   selector_ldt = SELECTOR_LDT_FIRST;
        u8    privilege;
        u8    rpl;
	int   eflags;
	int   i;
	int   prio;
	for (i = 0; i < NR_TASKS+NR_PROCS; i++) {
	        if (i < NR_TASKS) {     /* 任务 */
                        p_task    = task_table + i;
                        privilege = PRIVILEGE_TASK;
                        rpl       = RPL_TASK;
                        eflags    = 0x1202; /* IF=1, IOPL=1, bit 2 is always 1 */
			prio      = 15;
                }
                else {                  /* 用户进程 */
                        p_task    = user_proc_table + (i - NR_TASKS);
                        privilege = PRIVILEGE_USER;
                        rpl       = RPL_USER;
                        eflags    = 0x202; /* IF=1, bit 2 is always 1 */
			prio      = 5;
                }

		strcpy(p_proc->name, p_task->name);	/* name of the process */
		p_proc->pid = i;			/* pid */

		p_proc->ldt_sel = selector_ldt;

		memcpy(&p_proc->ldts[0], &gdt[SELECTOR_KERNEL_CS >> 3],
		       sizeof(struct descriptor));
		p_proc->ldts[0].attr1 = DA_C | privilege << 5;
		memcpy(&p_proc->ldts[1], &gdt[SELECTOR_KERNEL_DS >> 3],
		       sizeof(struct descriptor));
		p_proc->ldts[1].attr1 = DA_DRW | privilege << 5;
		p_proc->regs.cs	= (0 & SA_RPL_MASK & SA_TI_MASK) | SA_TIL | rpl;
		p_proc->regs.ds	= (8 & SA_RPL_MASK & SA_TI_MASK) | SA_TIL | rpl;
		p_proc->regs.es	= (8 & SA_RPL_MASK & SA_TI_MASK) | SA_TIL | rpl;
		p_proc->regs.fs	= (8 & SA_RPL_MASK & SA_TI_MASK) | SA_TIL | rpl;
		p_proc->regs.ss	= (8 & SA_RPL_MASK & SA_TI_MASK) | SA_TIL | rpl;
		p_proc->regs.gs	= (SELECTOR_KERNEL_GS & SA_RPL_MASK) | rpl;

		p_proc->regs.eip = (u32)p_task->initial_eip;
		p_proc->regs.esp = (u32)p_task_stack;
		p_proc->regs.eflags = eflags;

		p_proc->nr_tty		= 0;
                p_proc->sleep_ticks = 0;
		p_proc->p_flags = 0;
		p_proc->p_msg = 0;
		p_proc->p_recvfrom = NO_TASK;
		p_proc->p_sendto = NO_TASK;
		p_proc->has_int_msg = 0;
		p_proc->q_sending = 0;
		p_proc->next_sending = 0;
                
		p_proc->ticks = p_proc->priority = prio;

		p_task_stack -= p_task->stacksize;
		p_proc++;
		p_task++;
		selector_ldt += 1 << 3;
	}

        proc_table[NR_TASKS + 0].nr_tty = 1;
        proc_table[NR_TASKS + 1].nr_tty = 1;
        proc_table[NR_TASKS + 2].nr_tty = 1;
        proc_table[NR_TASKS + 3].nr_tty = 1;
        proc_table[NR_TASKS + 4].nr_tty = 1;
	k_reenter = 0;
	ticks = 0;
        customerNum = 0;
	p_proc_ready	= proc_table;
  mutex.value = 1;
  waiting = 0;
  CHAIR = 2;
	init_clock();
  init_keyboard();

	restart();

	while(1){}
}


/*****************************************************************************
 *                                get_ticks
 *****************************************************************************/
PUBLIC int get_ticks() {
	MESSAGE msg;
	reset_msg(&msg);
	msg.type = GET_TICKS;
	send_recv(BOTH, TASK_SYS, &msg);
	return msg.RETVAL;
}

PUBLIC int getProcSleepTicks() {
  MESSAGE msg;
	reset_msg(&msg);
	msg.type = GET_PROC_SLEEP_TICKS;
	send_recv(BOTH, TASK_SYS, &msg);
	return msg.RETVAL;
}
PUBLIC int goToSleep(int milliSec) {
  sleep(milliSec);
  currentSleep  = 1;
  while(currentSleep){};
  return 0;
}
/*======================================================================*
                               TestA
 *======================================================================*/
void TestA() { while (1){} }
/*======================================================================*
                               TestB
 *======================================================================*/

void TestB() {
  printf("\n");
  while (1) {
   p(&customers);
   p(&mutex);
   waiting--;
   printf("barber cutting!\n");
   v(&mutex);
   v(&barbers);
   goToSleep(60);
  }
}

/*======================================================================*
                               TestB
 *======================================================================*/
void TestC() {
  int i  = 1;
  while (1) {
	   /* assert(0); */
    p(&mutex);
    printf("customer  %d come!\n",++customerNum);
    i = customerNum;
    if (waiting < CHAIR) {
      printf("customer  %d wait! \n",i);
      waiting++;
      v(&customers);
      v(&mutex);
      p(&barbers);
      printf("customer  %d get service!\n",i);
    } else {
      v(&mutex);
      printf("not enough chairs!-------------------\n");
    }
    printf("customer  %d leaves\n",i);
    ++i;
    goToSleep(5);
  }

}
void TestD() {
  int i  = 1;
  while (1) {
	/* assert(0); */
    p(&mutex);
    printf("customer  %d come!\n",++customerNum);
    i = customerNum;
    if (waiting < CHAIR) {
      printf("customer  %d wait!\n",i,waiting);
      waiting++;
      v(&customers);
      v(&mutex);
      p(&barbers);
      printf("customer  %d get service!\n",i);
    } else {
      v(&mutex);
      printf("not enough chairs!-------------------\n");
    }
    printf("customer  %d leaves\n",i);
    ++i;
    goToSleep(5);
  }
}
void TestE() {
  int i  = 1;
  while (1) {
	/* assert(0); */
    p(&mutex);
    printf("customer  %d come!\n",++customerNum);
    i = customerNum;
    if (waiting < CHAIR) {
      printf("customer  %d wait!\n",i,waiting);
      waiting++;
      v(&customers);
      v(&mutex);
      p(&barbers);
      printf("customer  %d get service!\n",i);
    } else {
      v(&mutex);
      printf("not enough chairs!-------------------\n");
    }
    printf("customer  %d leaves\n",i);
    ++i;
    goToSleep(5);
  }
}

/*****************************************************************************
 *                                panic
 *****************************************************************************/
PUBLIC void panic(const char *fmt, ...) {
	int i;
	char buf[256];

	/* 4 is the size of fmt in the stack */
	va_list arg = (va_list)((char*)&fmt + 4);

	i = vsprintf(buf, fmt, arg);

	printl("%c !!panic!! %s", MAG_CH_PANIC, buf);

	/* should never arrive here */
	__asm__ __volatile__("ud2");
}

