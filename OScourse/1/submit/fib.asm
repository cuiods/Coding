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
section .bss
	outputresult: resd 1
    result0: resd 1
    result1: resd 1
    result2: resd 1
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
	call ShowGreen
	call output_result
	call ShowBlue
	call getInput
	call fib
	call output_result
	jmp main_start
	call exit

fib:
	xor ebx, ebx
	xor ecx, ecx
	mov edx, 1
	mov dword[lowNUm], 0
	mov dword[middleNum], 0
	mov dword[highNum], 0
	mov word[index], 0
fib_start:
	mov dword[result0], edx
	mov dword[result1], ecx
	mov dword[result2], ebx 
	mov eax, dword[lowNUm]
	add edx, eax
	mov eax, dword[middleNum]
	adc ecx, eax
	mov eax, dword[highNum]
	adc ebx, eax
	xor eax, eax
	mov ax, word[index]
	inc ax
	mov word[index], ax
	cmp ax, word[current]
	je fib_return
	mov eax, dword[result0]
	mov dword[lowNUm], eax
	mov eax, dword[result1]
	mov dword[middleNum], eax
	mov eax, dword[result2]
	mov dword[highNum], eax
	jmp fib_start
fib_return:
	mov dword[result0], edx
	mov dword[result1], ecx
	mov dword[result2], ebx 
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
	cmp dword[result2], 0
	je two_out
	mov eax, dword[result2] 
	mov dword[outputresult], eax
	call output
two_out:
	cmp dword[result1], 0
	je one_out
	mov eax, dword[result1]
	mov dword[outputresult], eax
	call output
one_out:
	mov eax, dword[result0]
	mov dword[outputresult], eax
	call output

	mov dword[result0], 0
	mov dword[result1], 0
	mov dword[result2], 0
	mov dword[outputresult], 0
	mov word[current], 0

	mov eax, 4
	mov ebx, 1
	mov ecx, nextLine
	mov edx, size_nextLIne
	int 80h

	ret

output:
	;add stack
	mov eax, dword[outputresult]
	xor edx, edx
	mov ebx, 10
	mov ecx, 1
pushStack:
	div ebx
	push edx
	cmp eax, 0
	je popStack
	inc ecx
	xor edx, edx
	jmp pushStack
popStack:
	mov byte[weishu], cl
popstart:
	pop eax
	add al, 30h
	mov byte[outChar], al

	mov eax,4
	mov ebx,1
	mov ecx,outChar
	mov edx,1
	int 80h

	mov cl, byte[weishu]
	dec cl
	cmp cl, 0
	mov byte[weishu], cl
	je exit_this
	jmp popstart
exit_this:
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