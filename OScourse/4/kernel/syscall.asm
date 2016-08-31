; ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
;                               syscall.asm
; ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
;                                                     Forrest Yu, 2005
; ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

%include "sconst.inc"

INT_VECTOR_SYS_CALL equ 0x90
_NR_printx	    equ 0
_NR_sendrec	    equ 1
_NR_sleep	    equ 2
_NR_P           equ 3
_NR_V		    equ 4
; 导出符号
global	printx
global	sendrec
global  sleep
global  p
global  v
bits 32
[section .text]

; ====================================================================================
;                  sendrec(int function, int src_dest, MESSAGE* msg);
; ====================================================================================
; Never call sendrec() directly, call send_recv() instead.
sendrec:
	mov	eax, _NR_sendrec
	mov	ebx, [esp + 4]	; function
	mov	ecx, [esp + 8]	; src_dest
	mov	edx, [esp + 12]	; p_msg
	int	INT_VECTOR_SYS_CALL
	ret

; ====================================================================================
;                          void printx(char* s);
; ====================================================================================
printx:
	mov	eax, _NR_printx
	mov	edx, [esp + 4]
	int	INT_VECTOR_SYS_CALL
	ret
sleep:
        mov     eax,_NR_sleep
        mov	edx,[esp + 4]
        int	INT_VECTOR_SYS_CALL
        ret

p:      mov      eax,_NR_P
        mov      edx,[esp+4]
        int 	 INT_VECTOR_SYS_CALL
        ret

v:      mov      eax,_NR_V
        mov      edx,[esp+4]
        int 	INT_VECTOR_SYS_CALL
        ret