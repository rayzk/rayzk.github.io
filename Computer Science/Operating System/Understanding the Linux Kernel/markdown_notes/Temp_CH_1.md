# Chapter 1 绪论

@(Understanding the Linux Kernel)[Chapter 1|Book Review]

--------------------------------------------------

[TOC]

--------------------------------------------------

**Unix-like System**
- SRV4 from AT&T
- BSD 
- Digital Unix from HP
- AIX from IBM
- Solaris from Sun
- Mac OS X from Apple
- FreeBSD
- NetBSD
- **Linux**

### 类Unix 的特征
- 单块结构和内核
- 编译，静态链接的Unix内核 ： 动态的装载部分内核代码（成为模块）
- 内核线程(kernel thread) : Linux 使内核线程周期性的执行内核函数
- 多线程应用程序支持 ：Linux定义了自己的轻量级线程(lightweight process, LWP)
- preemptive kernel : Linux可以交错执行处于特权模式的执行流
- 多处理器支持 ：Linux使用内核锁支持SMP和NUMA。Linux几乎以最优的方式使用SMP
- 文件系统：<!-- 文件系统 ext2 & ext3-->  Linux支持Ext2, Ext3, Journaling File System(JFS)
- Linux 并没有类似STREAMS的子系统 （STREAMS I/O子系统影响效率而被舍弃）

### Linux 的特点
- 充分可定制系统
- 运行在低档的平台上
- 内核小，紧凑
- 与通用OS兼容，
- 对很多网络层操作：以太网，Fiber Distributed Data Interface, High Performance Parallel Interface, IEEE 802.11(WiFi), IEEE 802.15(blue tooth)

### Linux 的硬件依赖
Linux在与硬件无关的源代码和硬件相关的代码之间保持界限。在arch和include下包含的子目录用来支持不同的硬件平台

### Linux 的版本
有三个数字秒数版本，前两个为版本号，第三个为发布号。第二位版本号为内核类型（偶数表示稳定内核，奇数表示开发中内核）
如果有第四位数，则表示补丁的版本

### OS
#### Multiuser System
- concurrently and independent
- protection

#### User and User Group
- user ID
- user group ID
- superuser 

#### Process
- scheduler 
- preemptable
- user/kernel mode

#### Kernel Structure
- module
- microkernel

#### Unix File System
- File/Dir Link : 
	- hard link
	- soft link/symbolic link
- File type :
	- reguler file
	- directory
	- symbolic link
	- block-oriented device file
	- character-oriented device file
	- pipe and named pipe (FIFO)
	- socket
- File Description and inode
- Access Permission :
	- suid
	- sgid
	- sticky
- File Operation System Call :
	- since file system is in user mode and thus cannot interact with hardware, all file operation must be executed under kernel mode and Unix provide system calls for file system operations
	- open (path, flag, mode) return file descriptor
	- lseek (fd, offset, whence)
	- read (fd, buf, count)
	- close (fd)
	- rename (oldpath, newpath)
	- unlink (pathname)

### Unix Kernel
#### Kernel/User mode in Unix
- Unix 只用到了 kernel/user mode
- Kernel 是进程的管理者；user mode application通过系统调用执行硬件相关的CPU instruction，完成user mode 到 kernel mode 的转换
- Kernel thread : run in kernel mode in kernel space; do not interact with user, thus do not need terminal device; usually created at system boot and remain active until system shutdown

#### Process Implementation 
- each process has a process descriptor : 
	- PC and SP
	- Regular Register
	- Float-point Register
	- Controller Register (Processor Status Word)
- 每个队列对应一组等待特定事件的进程

#### Reentrant Kernel
- Allow several processes in the kernel mode at the same time, however, only one is executed by CPU, the others are blocked for some events to happen. Interrupt will be sent to kernel
- 提供“可重入”的方法：编写只能修改局部变量，而不能修改全局数据结构的函数（可重入函数）；也可利用锁死机制保证一次只有一个进程执行一个非重入函数
- Kernel Control Path : 表示内核处理system call, exception, interruption 的指令序列

#### 进程的地址空间
- Address space of process in User mode includes : 私有栈，数据区，代码区
- 进程在访问kernel时，使用kernel的数据，代码区，使用另外的私有栈
- 交错执行的 kernel control path have their own 私有栈
- 共享地址空间 ： 例如编译程序

#### Semaphore
- 整数变量 + 进程的链表 + 两个 atomic operation : down() and up()
- 将进程插入到链表中并挂起进程：是两个费时的操作

#### Spin Lock
#### Deadlock
#### Signal
- Signal 提供了把系统事件报告给进程的一种机制
- SIGINT
- SIGSEGV

#### 进程管理
- fork ()
- _exit ()

#### Process Group
``` bash
ls | sort | more
```
#### Memory Management
- Memory Management Unit (MMU)

#### Virtual Memory
#### Kernel Memory Allocator (KMA)
#### Device Driver



