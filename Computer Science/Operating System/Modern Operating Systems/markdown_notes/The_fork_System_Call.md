# The fork() System Call

### Return Value of `fork()`
- if `fork()` return a negative value, the creation of child process is unsuccessful. 
- `fork()` returns a zero to the newly created child process
- `fork()` returns a positive value, *the process ID of child process* to the parent

> Process ID is of type `pid_t` defined in **sys/types.h**. Normally the process ID is an integer, process can retrieve the process ID by calling `getpid()`

### Execution flow After `fork()`
Both processes start their execution after the system call `fork()`. The variable initialized before the `fork()` call have the same value in both address space

`printf()` is buffered. It will group the output of process together. As the message will not be sent to the screen immediately, the right order of execution maynot be captured. 
``` cpp
sprintf (buf, " content \n");
write (1, buf, strlen(buf));
```
`write()` is not buffered.