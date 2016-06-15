section .data
	lowNUm: dd 0
	middleNum: dd 0
	highNum: dd 0
	msg_input: db "Enter the numbers:"
	size_inputMsg: equ $-msg_input
	nextLine: db 0Ah
	size_nextLIne: equ $-nextLine
	result: dd 0
	index: dw 1
	red: db 27,"[0;31m"
	size_red: equ $-red
	green: db 27,"[0;32m"
	size_green: equ $-green
	blue: db 27,"[0;34m"
	size_blue: equ $-blue
	none: db 27,"[0m"
	size_none: equ $-none
	current: dw 0
	fibOne: times 32 db 0
	fibTwo: times 32 db 0
	carryFlag: db 0
	zeroByte: db 0
section .bss
	outputresult: resd 1
    digit0: resb 1
    digit1: resb 1
    digit2: resb 1
    space: resb 1
    temp: resw 1
    weishu: resb 1
    outChar: resb 1

section .text
	GLOBAL main

main:
	;print message to enter the numbers
	mov eax, 4
	mov ebx, 1
	mov ecx, msg_input
	mov edx, size_inputMsg
	int 80h
main_start:
	call getInput
	call fib
	call ShowRed
	call output_result
	call getInput
	call fib
	call ShowRed
	call output_result
	call ShowBlue
	call getInput
	call fib
	call output_result
	jmp main_start
	call exit

fib:
	;set zero
	mov ecx, 0
fib_zero:
	cmp ecx, 32
	je fib_start
	mov byte[fibOne+ecx], 0
	mov byte[fibTwo+ecx], 0
	inc ecx
	jmp fib_zero
fib_start:
	mov ecx, 31
	mov edx, 1
	mov byte[fibTwo+31], 1
fib_loop:
	cmp dx, word[current]
	je fib_exit
	mov ecx, 31
oneAddloop:
	cmp ecx, 0
	je fib_next
	movzx eax, byte[fibOne+ecx]
	movzx ebx, byte[fibTwo+ecx]
	call myAdc
	movzx ebx, byte[fibTwo+ecx]
	mov byte[fibOne+ecx], bl
	mov byte[fibTwo+ecx], al
	dec ecx
	jmp oneAddloop
fib_next:
	inc dx
	jmp fib_loop
fib_exit:
	ret

myAdd:
    mov byte[carryFlag], 0
	add eax, ebx
	cmp eax, 10
	jb add_exit
	sub eax, 10
	mov byte[carryFlag], 1
add_exit:
	ret

myAdc:
	push ecx
	movzx ecx, byte[carryFlag]
	add eax, ebx
	add eax, ecx
	cmp eax, 10
	jb setCarry
	sub eax, 10
	mov byte[carryFlag], 1
	jmp adc_exit
setCarry:
	mov byte[carryFlag], 0
adc_exit:
	pop ecx
	ret

getInput:
	mov byte[digit0], 0
	mov byte[digit1], 0
	mov byte[digit2], 0
	;get input number0
	mov eax, 3
	mov ebx, 0
	mov ecx, digit0
	mov edx, 1
	int 80h
	sub byte[digit0], 30h

	;get input number1
	mov eax, 3
	mov ebx, 0
	mov ecx, digit1
	mov edx, 1
	int 80h
	;compare end of all numbers
	cmp byte[digit1], 0Ah
	je exit_last1
	;compare end of this number
	cmp byte[digit1], 20h
	je calNUmber1

	;get input number2
	mov eax, 3
	mov ebx, 0
	mov ecx, digit2
	mov edx, 1
	int 80h
	;compare end of all numbers
	cmp byte[digit2], 0Ah
	je exit_last2
	;compare end of this number
	cmp byte[digit2], 20h
	je calNUmber2
	;read last space
	mov eax, 3
	mov ebx, 0
	mov ecx, space
	mov edx, 1
	int 80h
	cmp byte[space], 0Ah
	je exit_last
	jmp calNUmber

calNUmber1:
	mov byte[digit1],0
	jmp calNUmber
calNUmber2:
	mov byte[digit2],0
	jmp calNUmber
calNUmber:;calculate the real number
	cmp byte[digit2],0
	je twodigit
	sub byte[digit2], 30h
	sub byte[digit1], 30h
	mov al, byte[digit0]
	mov dl, 100
	mul dl
	mov word[temp],ax
	mov al, byte[digit1]
	mov dl, 10
	mul dl
	add ax, word[temp]
	movzx bx, byte[digit2]
	add ax, bx
	mov word[current], ax
	ret
twodigit:
	cmp byte[digit1], 0
	je onedigit
	sub byte[digit1], 30h
	xor ax, ax
	mov al, byte[digit0]
	mov dl, 10
	mul dl
	add al, byte[digit1]
	adc ah, 0
	mov word[current], ax
	ret
onedigit:
	movzx ax, byte[digit0]
	mov word[current], ax
	ret

output_result:
	mov ecx, 0
	mov byte[zeroByte], 0
out_loop:
	cmp ecx, 32
	je out_exit
	movzx eax, byte[fibTwo+ecx]
	cmp eax, 0
	je zero_handle
	mov byte[zeroByte], 1
con:
	add eax, 30h
	mov byte[fibTwo+ecx], al
	push ecx
	mov eax, 4
	mov ebx, 1
	add ecx, fibTwo
	mov edx, 1
	int 80h
	pop ecx
	inc ecx
	jmp out_loop
zero_handle:
	inc ecx
	mov bl, byte[zeroByte]
	cmp ebx, 0
	je out_loop
	dec ecx
	jmp con
out_exit:
	mov byte[zeroByte], 0
	mov eax, 4
	mov ebx, 1
	mov ecx, nextLine
	mov edx, size_nextLIne
	int 80h
	ret

output:
	
	ret


ShowRed:
    mov eax,4
	mov ebx,1
	mov ecx,red
	mov edx,size_red
	int 80h
    ret

ShowGreen:
    mov eax,4
	mov ebx,1
	mov ecx,green
	mov edx,size_green
	int 80h
    ret

ShowBlue:
    mov eax,4
	mov ebx,1
	mov ecx,blue
	mov edx,size_blue
	int 80h
    ret

ShowNone:
    mov eax,4
	mov ebx,1
	mov ecx,none
	mov edx,size_none
	int 80h
    ret

exit_last1:
	call calNUmber1
	call fib
	call output_result
	call exit

exit_last2:
	call calNUmber2
	call fib
	call output_result
	call exit

exit_last:
	call calNUmber
	call fib
	call output_result
	call exit

exit:
	call ShowNone
	mov eax, 1
	mov ebx, 0
	int 80h