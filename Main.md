# Java 控制台数据库程序知识点体系

> 依据题目：TV Show 数据库（文件读入 → 菜单增删改查排序 → 写盘保存）
> 本文把题目涉及的知识点整理成体系，可直接当复习提纲用。

---

## 一、文件流 IO 体系

### 1.1 IO 流的分类框架

```
java.io
├── 字节流（8位，适合图片/音频等二进制）
│   ├── InputStream / OutputStream（抽象基类）
│   └── FileInputStream / FileOutputStream
└── 字符流（16位，适合文本）
    ├── Reader / Writer（抽象基类）
    ├── 转换桥梁：InputStreamReader（字节→字符）、OutputStreamWriter
    ├── 文件实现：FileReader / FileWriter
    └── 缓冲包装：BufferedReader / BufferedWriter（本题主角）
```

**设计思想——装饰器模式**：流可以一层层包。

```java
new BufferedReader(new FileReader(filename))
//      ↑缓冲装饰        ↑真正的文件读取源
```

- `FileReader` 只负责"从文件读字符"，没有缓冲，每次读一个字符效率低
- `BufferedReader` 套在外面，加一块内存缓冲区，并提供 `readLine()` 按行读的能力
- 写方向同理：`new BufferedWriter(new FileWriter(filename))`

### 1.2 核心类 API 速查

| 类 | 常用方法 | 说明 |
|---|---|---|
| `BufferedReader` | `readLine()` | 读一行，**返回 null 表示流结束**；阻塞式 |
| `BufferedWriter` | `write(String)` / `newLine()` / `close()` | 写一行内容 / 写换行 / 刷新并关闭 |
| `FileWriter` | 构造器 `FileWriter(path)` | 默认**覆盖**模式；`FileWriter(path, true)` 为追加模式 |
| `File` | `exists()` / `getName()` | 表示文件路径本身，可先判断存在性 |

### 1.3 读文件标准模板

```java
BufferedReader reader = null;
try {
    reader = new BufferedReader(new FileReader(filename));
    String line;
    while ((line = reader.readLine()) != null) {   // null = 文件读完
        // 处理每一行
    }
} catch (FileNotFoundException e) {
    System.out.println("cannot open '" + filename + "'");
} catch (IOException e) {
    System.out.println("IO error: " + e.getMessage());
} finally {
    if (reader != null) {
        try { reader.close(); } catch (IOException e) { }
    }
}
```

**try-with-resources（JDK7+ 自动关流，推荐）**：

```java
try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
    // 用 reader，结束时自动 close，即使抛异常也关
} catch (IOException e) { ... }
```

### 1.4 写文件标准模板

```java
try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
    writer.write(count + "\n");        // 首行写条数
    for (int i = 0; i < count; i++) {
        writer.write(name[i] + "\n" + date[i] + "\n" + time[i] + "\n");
    }
}   // try 块结束自动 close → 缓冲区数据真正落盘
```

**要点**：
- **不 close 就可能丢数据**——数据可能还躺在缓冲区里没写到磁盘
- `FileWriter` 默认覆盖原文件；要追加用 `new FileWriter(path, true)`
- `\n` 是换行符，跨平台更规范的是 `writer.newLine()`

### 1.5 自定义文件格式（本题设计）

```
3              ← 首行：记录条数 n
Breaking Bad
15
22.5
Friends
7
19.0
...
```

**读写必须对称**：写的时候"首行 n + 每条 3 行"，读的时候也按这个结构解析。这是这类题的核心设计。

### 1.6 键盘输入：BufferedReader vs Scanner

| | BufferedReader | Scanner |
|---|---|---|
| 创建 | `new BufferedReader(new InputStreamReader(System.in))` | `new Scanner(System.in)` |
| 读一行 | `readLine()` 返回 String | `nextLine()` |
| 读整数 | `Integer.parseInt(readLine().trim())` | `nextInt()` |
| 读小数 | `Double.parseDouble(readLine().trim())` | `nextDouble()` |
| 特点 | 需手动转类型，但无"回车残留"问题 | 方便，但 `nextInt()` 后接 `nextLine()` 会读到空行 |
| 异常 | 必须处理 IOException | 不抛受检异常 |

**默认查找位置**：相对路径从**当前工作目录**找，IDE 中通常是项目根目录，可用 `System.getProperty("user.dir")` 查看。

---

## 二、异常体系

### 2.1 异常类继承结构

```
Throwable
├── Error（严重系统错误，程序不处理，如 OutOfMemoryError）
└── Exception
    ├── RuntimeException（非受检异常，编译器不强制处理）
    │   ├── NullPointerException          空指针
    │   ├── ArrayIndexOutOfBoundsException 数组越界
    │   ├── NumberFormatException          字符串转数字失败
    │   ├── ArithmeticException            除零（整数）
    │   └── IllegalStateException          状态非法
    └── 其他受检异常（编译器强制 try-catch 或 throws）
        ├── IOException                    IO 操作失败
        ├── FileNotFoundException          文件不存在（IOException 的子类）
        └── InterruptedException           线程中断
```

**受检 vs 非受检**：`Exception` 下除 `RuntimeException` 家族外都是受检异常，方法签名必须 `throws` 或就地 `try-catch`，否则编译不过。

### 2.2 try-catch-finally 语法

```java
try {
    // 可能出错的代码
} catch (FileNotFoundException e) {      // 子类异常要写在前面
    System.out.println("file not found");
} catch (IOException e) {                // 父类兜底在后
    System.out.println("io error: " + e.getMessage());
} finally {
    // 无论是否异常都执行，通常放资源释放
}
```

**匹配规则**：抛出异常时从上往下找第一个"是该异常类或其父类"的 catch 块。子类 catch 放在父类之后会编译报错。

### 2.3 throws 声明上抛

```java
public static void main(String[] args) throws IOException { ... }
```

不想就地处理时，把异常抛给调用者。`main` 上抛 = 最终交给 JVM，程序打印堆栈后终止。

### 2.4 本题用到的三个异常及处理策略

| 异常 | 触发场景 | 处理策略 |
|---|---|---|
| `FileNotFoundException` | 打开不存在的文件 | 提示后**循环重试**输入文件名 |
| `NumberFormatException` | `parseInt("abc")`、行尾混入隐藏字符 | 提示**重新输入**（while + try-catch 模板） |
| `IOException` | 读写出错 | 打印信息，能恢复则恢复 |

### 2.5 健壮输入万能模板（必背）

```java
int value = 0;
while (true) {
    System.out.print("input: ");
    try {
        value = Integer.parseInt(in.readLine().trim());
        if (value < 1 || value > 100) {
            System.out.println("out of range");
            continue;
        }
        break;                              // 合法才跳出
    } catch (NumberFormatException e) {
        System.out.println("not a number, try again");
    }
}
```

---

## 三、面向对象：封装

### 3.1 封装三要素

1. **属性 private**：外界不能直接访问 `show.time = -999`
2. **提供 public getter/setter**：受控访问，可在方法里加校验
3. **构造器**：创建对象时初始化全部字段

### 3.2 封装类标准写法

```java
class TVShow {
    private String name;
    private int date;       // 1-31
    private double time;    // 0-24

    public TVShow(String name, int date, double time) {   // 构造器
        this.name = name;        // this. 区分成员变量和参数
        this.date = date;
        this.time = time;
    }

    public String getName()  { return name; }
    public int getDate()     { return date; }
    public double getTime()  { return time; }

    public void setDate(int date)  { this.date = date; }
    public void setTime(double t)  { this.time = t; }

    @Override
    public String toString() {      // 决定 println/打印时的显示形式
        return String.format("%-20s %6d %9.1f", name, date, time);
    }
}
```

### 3.3 封装类 vs 平行数组

| | 平行数组 | 封装类 + 对象数组 |
|---|---|---|
| 一条记录 | 三个数组同一下标拼成 | 一个对象 |
| 排序/删除 | 三个数组必须同步交换，易错位 | 只搬一个引用 |
| 方法签名 | `(String[] n, int[] d, double[] t, int count, ...)` | `(TVShow[] shows, int count, ...)` |
| 数据保护 | 无，任何代码可乱写 | private + setter 校验 |

### 3.4 this 关键字

`this.name = name;`——左边是成员变量，右边是构造器参数。`this` 指当前对象本身，成员变量与局部变量同名时必须用它区分。

---

## 四、排序体系

### 4.1 冒泡排序（手写，必背）

原理：每轮从头开始，相邻两个比较，逆序就交换；每一轮把当前最大值"冒泡"到末尾，末尾有序区逐渐扩大。

```java
// 升序：从小到大
for (int end = count - 1; end > 0; end--) {     // 外层控制有序区边界
    for (int i = 0; i < end; i++) {             // 内层只比到边界前
        if (arr[i] > arr[i + 1]) {
            int tmp = arr[i];                   // 三步交换（借助临时变量）
            arr[i] = arr[i + 1];
            arr[i + 1] = tmp;
        }
    }
}
```

**记忆要点**：
- 外层 `count-1` 轮，内层逐轮缩短（`i < end`）
- 降序只需把 `>` 改成 `<`
- **平行数组/对象数组排序时，交换必须整条记录一起换**（对象数组只换一个引用即可）：

```java
TVShow tmp = shows[i];
shows[i] = shows[i + 1];
shows[i + 1] = tmp;
```

### 4.2 Comparable——类自带的"天然排序"

```java
class TVShow implements Comparable<TVShow> {
    @Override
    public int compareTo(TVShow other) {
        return Double.compare(this.time, other.time);   // <0 在前，=0 相等，>0 在后
    }
}

// 使用
Arrays.sort(shows, 0, count);   // 按 compareTo 定义的规则排
```

- `compareTo` 返回负数表示 `this` 排前面
- 基本类型比较用 `Integer.compare(a, b)` / `Double.compare(a, b)`，不要用减法（可能溢出）
- 适合"只有一种自然顺序"的类

### 4.3 Comparator——外部多种排序规则（本题场景）

```java
import java.util.Arrays;
import java.util.Comparator;

// 主排序 + 次排序链式组合
Comparator<TVShow> byTime = Comparator
        .comparingDouble(TVShow::getTime)
        .thenComparingInt(TVShow::getDate);    // time 相同再按 date

Comparator<TVShow> byDate = Comparator
        .comparingInt(TVShow::getDate)
        .thenComparingDouble(TVShow::getTime);

Arrays.sort(shows, 0, count, byTime);          // 只排前 count 个有效元素
```

- `Comparator` 写在类外部，**不改实体类**就能定义任意多种排序规则
- `TVShow::getTime` 是**方法引用**，等价于 lambda `s -> s.getTime()`
- `Arrays.sort(arr, fromIndex, toIndex, comparator)`：只排指定区间

**选择口诀**：一种顺序用 `Comparable`（类自己实现），多种顺序切换用 `Comparator`（外部定义）。

---

## 五、数组与常用算法模式

### 5.1 数组基础

```java
int[] arr = new int[6400];        // 声明+分配，元素自动初始化为 0/null/false
arr.length                        // 容量（不是实际条数！）
arr[i]                            // 下标从 0 开始，越界抛 ArrayIndexOutOfBoundsException
```

**容量 vs 实际长度**：数组容量固定，用 `count` 记录实际条数，遍历上限永远是 `count`。

### 5.2 查找模式（含 found 标志）

```java
int flag = -1;                          // -1 表示"没找到"的约定值
for (int i = 0; i < count; i++) {
    if (shows[i].getName().equals(target)) {
        flag = i;
        break;                          // 找到就停
    }
}
if (flag == -1) {
    System.out.println("Item not found");
    return;                             // ★ 找不到必须有出口，否则后面越界崩溃
}
```

### 5.3 删除 = 前移补位（数组没有"删除"）

```java
// 方法一：整体左移一格（对象数组首选）
for (int i = flag; i < count - 1; i++) {
    shows[i] = shows[i + 1];            // 后面的往前搬
}
shows[count - 1] = null;                // 清掉最后一个
count--;

// 方法二：双指针（w 写指针，r 读指针），适合跳过任意位置的空洞
int w = 0;
for (int r = 0; r < count; r++) {
    if (要保留 arr[r]) {
        arr[w++] = arr[r];
    }
}
count = w;
```

### 5.4 新增 = 尾部插入

```java
if (count >= capacity) { /* 满了，拒绝 */ }
arr[count++] = new Element(...);        // 写在 count 位置，再自增
```

---

## 六、循环与分支体系

### 6.1 三种循环的分工

| 循环 | 适用场景 | 本题例子 |
|---|---|---|
| `while(true)` | 不知道要跑多少次，内部决定退出 | 菜单主循环、输入重试、文件名重试 |
| `for` | 次数确定（遍历 0..count-1） | 打印全部、逐条读文件、冒泡 |
| `do-while` | 至少执行一次 | 先展示菜单再判断（可替代 while(true)） |

### 6.2 循环控制关键字

- `break`：跳出**当前层**循环（嵌套时只跳一层）
- `continue`：跳过本次循环剩余部分，进入下一轮
- `return`：直接结束方法（菜单退出用它）

### 6.3 菜单驱动骨架（必背）

```java
while (true) {
    printMenu();
    int choice = readValidatedInt(in, "choose: ", 1, 8);
    switch (choice) {
        case 1: addShow(...);    break;    // ★ 每个 case 记得 break
        case 2: modifyShow(...); break;
        case 8: return;                    // 退出
    }
    // 执行完自动回到菜单顶部
}
```

### 6.4 分支判断要点

- `if-else if-else`：范围判断、互斥条件
- `switch`：等值分发（菜单选项、枚举状态）
- 标志位模式：`boolean found = false;` → 循环中置 true → 循环后判断
- `三元运算符`：`String s = (x > 0) ? "正" : "非正";` 适合简单二选一

---

## 七、字符串常用方法

| 方法 | 作用 | 示例 |
|---|---|---|
| `trim()` | 去首尾空白（空格/Tab/回车） | `"  ab  ".trim()` → `"ab"` |
| `equals(s)` | 内容精确比较（**不要用 ==**，== 比地址） | `name.equals("Tom")` |
| `equalsIgnoreCase(s)` | 忽略大小写比较 | `"TIME".equalsIgnoreCase("time")` → true |
| `length()` | 字符个数 | |
| `substring(a, b)` | 截取 [a, b) 区间 | |
| `contains(s)` | 是否包含子串 | |
| `split(regex)` | 按分隔符拆成数组 | |

**解析数字**：`Integer.parseInt("31")` → int；`Double.parseDouble("22.5")` → double。格式非法抛 `NumberFormatException`，所以解析前先 `trim()`。

**为什么 `==` 不能比字符串**：`==` 比较引用地址，两个内容相同但分别创建的字符串对象用 `==` 会得到 false。字符串是对象，比内容一律用 `equals()`。

---

## 八、结构化输出：printf 全语法

### 8.1 基本形式

```java
System.out.printf("格式串", 参数1, 参数2, ...);
```

格式串中 `%` 开头的是占位符，按顺序对应后面的参数。

### 8.2 占位符速查表

| 占位符 | 含义 | 示例 |
|---|---|---|
| `%d` | 整数 | `printf("%5d", 42)` → `···42` |
| `%f` | 小数（默认 6 位） | |
| `%.2f` | 保留 2 位小数 | `printf("%.1f", 22.55)` → `22.6` |
| `%s` | 字符串（也可接整数等，自动 toString） | |
| `%n` | 平台相关换行（推荐替代 `\n`） | |
| `%%` | 输出百分号本身 | |

### 8.3 宽度与对齐

```
%[宽度][.精度]类型
%[对齐符][宽度]类型
```

- `%6d`：占 6 格宽，**默认右对齐**（前面补空格）——适合数字列
- `%-20s`：占 20 格宽，**负号 = 左对齐**（后面补空格）——适合文字列
- 宽度不够时补空格；内容超宽时**不会被截断**，按实际长度输出

### 8.4 本题表格输出实例

```java
// 表头
System.out.printf("%3s  %-20s %6s %9s%n", "#", "NAME", "DATE", "TIME(h)");
// 数据行
System.out.printf("%3d. %-20s %6d %9.1f%n", i + 1, name, date, time);
```

输出效果（`.` 表示空格）：

```
..#..NAME..................··DATE···TIME(h)
..1..Breaking Bad............15....22.5
..2..Friends..................7....19.0
```

列宽上下一致，就形成对齐的表格。**配套原则**：表头和数据行用相同的宽度规格。

---

## 九、代码组织与风格

### 9.1 方法拆分原则

- 一个功能一个方法：`addShow / modifyShow / deleteShow / sortShows / printAll / saveShows / loadShows`
- `main` 只保留主流程骨架（初始化 → 菜单循环），细节全部下沉
- 重复代码抽成工具方法（如 `readInt / readDouble` 校验输入、`findByName` 查找）

### 9.2 参数传递

- 数组、对象传的是**引用**：方法内修改数组元素，调用方看得到
- `int` 等**基本类型**传的是**值副本**：方法内改不影响调用方 → 需要"改 count"的方法用返回值：`count = addShow(shows, count, in);`

### 9.3 static 方法

不依赖对象状态的工具方法声明为 `static`，直接 `类名.方法名()` 或同类中直接调用。

### 9.4 高频 bug 自检清单

1. **找不到元素时有没有 `return` / 提前结束？**（否则 flag=-1 去取元素 → 数组越界）
2. **下标会不会越界？**（`count` 做上限、新增前查容量、删除后 `count--`）
3. **写文件有没有 `close()`？**（否则缓冲区数据丢失，文件是空的）
4. **非法输入会不会崩溃？**（数字/范围/文件名，都要 while + try-catch 重试）
5. **读文件有没有判 `null`？**（行数不足时 readLine 返回 null）
6. **排序交换是不是整条记录一起换？**（平行数组三连换 / 对象换引用）
7. **字符串比较用的是 `equals` 而不是 `==`？**

---

## 十、举一反三：解题四问

遇到"XX 管理系统"类题目，按顺序回答：

1. **一条记录有哪些字段？** → 设计封装类（或平行数组）
2. **菜单要哪些功能？** → 增 / 删 / 改 / 查 / 排序 / 打印，逐个封装成方法
3. **要不要持久化？** → 设计对称的文件格式，Save 与 ReLoad 两个口子
4. **哪些输入可能非法？** → 每处套健壮输入模板

骨架永远不变：**建模 → 读入 → while(true)+switch 菜单循环 → 读写盘**，变的只是字段和文件名。
