# SOSD 4-11 公开课

为什么想开这堂课

其实这节课讲的内容是和框架设计底层相关的，难度可能会偏大。但是我觉得对于我们大家来说，想成为一个优秀的Java工程师，或者软件设计工程师不能只看重，我如何使用XXX技术，停留在这个表面。面对市面上多种框架的原理底层，似乎对我们来说是深不可测的，不敢探究的。如果这样

所谓Java工程师最后都会变成Spring工程师，甚至是SpringBoot工程师。离开了Spring，我们的Java语言又能做出什么事情？

所以我想以 RPC 框架的简单实现，为引子，让大家对框架底层设计有一个概念，同时真真切切的再去感受一下我们的Java语言

我们会接触到

- 代理模式

[Java 代理模式详解 | JavaGuide](https://javaguide.cn/java/basis/proxy.html)

- SPI

- 反射

## 课程大纲

预热

- 反射
- 代理模式
- SPI

### 反射（Reflection）

反射是 Java 提供的一种强大的机制，允许程序在运行时动态地检查类、获取类的信息（如字段、方法、构造函数等）以及在运行时调用类的方法。反射使得我们可以在编译时不知道某个类的具体信息的情况下，仍然能够操作该类的属性和方法。

反射的主要类是 `java.lang.reflect` 包下的 `Field`、`Method`、`Constructor` 等。通过这些类，我们可以动态地操作类的成员。

使用反射的一些常见场景包括：创建对象、调用类的方法、获取和设置对象的字段等。在框架中使用非常频繁

### 动态代理（Dynamic Proxy）

动态代理是一种在运行时创建代理类和对象的机制，它允许在不修改源代码的情况下，对原始类的方法进行增强或拦截。动态代理通常用于实现 AOP（面向切面编程），日志记录、事务管理等功能。

动态代理的优点是可以实现统一的逻辑处理，而不需要修改原始类的代码，从而使得代码更加灵活和可维护。

### SPI 机制（Service Provider Interface）

SPI 是 Java 提供的一种服务提供者接口，它允许应用程序在运行时发现和加载服务的实现。SPI 主要涉及两个角色：服务接口和服务实现。服务接口定义了一组抽象的行为，而服务实现则提供了服务接口的具体实现。

SPI 的核心在于在 `META-INF/services` 目录下提供一个以服务接口全限定名命名的文件，文件内容是实现该服务接口的类的全限定名。通过读取这个文件，应用程序可以动态地加载服务的实现，从而实现可插拔式的架构。

SPI 在 Java 中被广泛应用于诸如 JDBC 数据库驱动场景，使得应用程序可以更加灵活地适应不同的实现。

- 什么是 RPC？

> [RPC**](https://www.baidu.com/s?rsv_idx=1&wd=RPC&fenlei=256&usm=1&ie=utf-8&rsv_pq=9842c8c20065f7cd&oq=什么是RPC&rsv_t=8d66AkwR75KPE17VSOXfgsTH616b%2BVbfM25Xe3Yu8SEJucEGPHao7DEs%2F0A&sa=re_dqa_zy&icon=1)(Remote Procedure Call)，即远程过程调用，是一种软件通信协议，允许一个计算机程序通过网络调用另一个计算机程序中的子程序（也就是远程过程），并获取返回值。
>
> RPC实现调用远程主机上的方法就像调用本地方法一样，是典型的C/S（客户端/服务器）模型。在逻辑上，RPC可以分为几个层次，包括传输/网络协议、消息协议、服务器端的选择器/处理器和客户端的存根/代理。
>
> RPC广泛应用于分布式服务、分布式计算、远程服务调用等多种场景。
>
> [RPC基础知识总结 | JavaGuide](https://javaguide.cn/distributed-system/rpc/rpc-intro.html)

- 他和微服务架构的关系？

      画图 引入注册中心。本地服务通过注册中心去消费服务

- RPC框架运行的流程，和直接调用做对比。

- 根据流程推出架构 画图

- 如何设计一个基础的RPC框架

    - 生产者，消费者概念
    - 项目模块关系
    - 底层通信
        - 通信服务器搭建
        - 序列化
            - 如何实现多种序列化器？
            - SPI机制？
        - 构建请求，代理发送请求。（反射的应用）
    - 离开了SpringBoot我们如何读取配置？（配置类的构建）
    - 注册中心介绍，如何设计一个简单的注册中心？
        - 服务注册
        - 服务发现
        - 负载均衡
        - 心跳检测
    - 实现一次RPC远端服务调用

https://www.processon.com/diagraming/65222275ee3af44dd47023fc
