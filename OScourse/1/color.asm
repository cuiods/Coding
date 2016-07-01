
segment .data
              ; message in char,attrib,char,attrib... format
    msg1 db 'M',01h,'a',02h,'n',03h,'y',04h,' ',05h,'T',06h
        db 'h',07h,'a',08h,'n',09h,'k',0Ah,'s',0Bh,' ',0Ch
        db 't',0Dh,'o',0Eh,' ',0Fh,'R',01h,'a',02h,'l',03h
        db 'f',04h,' ',05h,'B',06h,'r',07h,'o',08h,'w',09h
        db 'n',0Ah,'!',0Bh      ; no need to terminate...
    msg1len dw $-msg1            ; let the assembler count 'em!

segment .text

    mov ax, 3        ; reset video mode to 3 (cheesy CLS)
    int 10h          ; call bios video services
domore:
    mov bp, msg1      ; point to string
    mov cx, ; length of string - chars + attributes
    shr cx, 1        ; div by two to get char-only len for the call
    mov dh, 0Bh      ; row
    mov dl, 1Ah      ; column
    mov bh, 0        ; video page "usually" (?) zero
                      ; don't care what BL is for this AL
    mov ah, 13h      ; write string in ES:BP at DH,DL (row,column)
    mov al, 2        ; char, attr,... format - no cursor update 
                      ; 3 - char,attrib - update  cursor
                      ; 1 - attrib in BL - update cursor
                      ; 0 - attrib in BL - no update
    int 10h          ; call video services (AT+,EGA+ ???)
                      ; rather than alter the pallette, we just
    mov bx, msg1      ; change the colors in the string, and
    mov cx, ; reprint it (no cursor update :) ! )
    shr cx, 1        ; number of colors
morecolors:
    inc bx            ; skip over character
    mov al,  2    ; get current color
    inc al            ; bump it
    cmp al, 10h      ; we only change low nibble - high nibble
    jnz colorok      ; is background (bit 7 set - FG blinks)
    mov al, 1        ; we don't want color zero (black), or we
colorok:              ; could just "or al,0Fh"
    mov bx, al      ; stuff it back in the string
    inc bx            ; point to next character
    loop morecolors  ; do 'em all
    mov cx, 2        ; BIOS is "slow", but we still
    call DELAYCX      ; need some delay
    mov ah, 1        ; check keyboard status
    int 16h
    jz domore        ; no key hit - do it again
    mov ah, 0        ; get the key off the buffer
    int 16h
                      ; could clearscreen again here, but
exit:                ; naw, let's leave it...
    mov ah, 4Ch      ; scram
    int 21h

;----------------------------------------------------------
; twiddle thumbs for cx/18.2 seconds
DELAYCX
        push ax
        push bx
        push ds
        mov bx, 40h        ; BIOS data segment
        mov ds, bx
        mov bx, 6Ch        ; BIOS timer, 40:6C
        mov ax, 
        add ax, cx
DelayLoop:
        cmp ax,         ; Is it the same?
        jnc DelayLoop      ; No, try again.
        pop ds              ; Restore registers and exit.
        pop bx
        pop ax
        ret