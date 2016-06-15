EXTERN _printStr_
EXTERN _length_

GLOBAL my_print

section .text

my_print:
    mov	eax,4
    mov	ebx,1
    mov ecx,[_printStr_]
	mov	edx,[_length_]	
	int	80h
ret