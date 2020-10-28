## 垃圾收集器

收集器 | cmd | 特点
---|--- | ---
串行 | -XX:+UseSerialGC | 单线程，串行执行，小堆时fullGC时间过长
并行 | -XX:+UseParallelGC | 多线程并行收集，吞吐量优先
CMS | +UseConcMarkSweepGC | 并发收集(用户线程可执行)，降低STW时间，标记清除会产生碎片。JAVA9废弃
G1 | -XX:+UseG1GC | CMS的升级版，吞吐量和低停顿兼顾，大堆有优势

