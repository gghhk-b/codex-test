# Spring Boot 3.x + Maven + Thymeleaf 

> 目标：让你像“搭积木”一样，一步步创建一个可运行的 Spring Boot Web 项目，并在浏览器看到真实页面，而不是返回字符串。

---

## 1. 环境准备

⚠️ **版本要求（必须）**

- **JDK 17+**（Spring Boot 3.x 的最低要求）
- **Maven 3.9+**（建议）
- 操作系统：Windows / macOS / Linux 均可

可以用以下命令检查环境：

```bash
java -version
mvn -version
```

---

## 2. 依赖配置（`pom.xml`）

💡 下面这个 `pom.xml` 是完整可用版本，包含：

- `spring-boot-starter-web`：提供 Web 能力（MVC、内嵌 Tomcat 等）
- `spring-boot-starter-thymeleaf`：提供模板引擎，负责把逻辑视图名解析为 HTML 文件

**File: `pom.xml`**

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.3.5</version>
        <relativePath/>
    </parent>

    <groupId>com.example</groupId>
    <artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>demo</name>
    <description>Spring Boot Thymeleaf Demo</description>

    <properties>
        <!-- Spring Boot 3.x 需要 JDK 17+ -->
        <java.version>17</java.version>
    </properties>

    <dependencies>
        <!-- Web 开发核心依赖：Spring MVC + 内嵌 Tomcat -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- 模板引擎依赖：将 Controller 返回的视图名映射到 templates 下的 HTML 文件 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

---

## 3. 目录搭建（重点看 `templates`）

⚠️ **HTML 文件必须放在：`src/main/resources/templates`**

项目结构如下：

```text
codex-test/
├─ pom.xml
└─ src/
   └─ main/
      ├─ java/
      │  └─ com/example/demo/
      │     ├─ Application.java
      │     └─ HomeController.java
      └─ resources/
         ├─ application.properties
         └─ templates/
            └─ index.html   ← 真实页面文件放这里
```

---

## 4. 编写 HTML 页面（真实网页文件）

💡 下面我们创建一个有简单样式的 `index.html`，让页面更直观。

**File: `src/main/resources/templates/index.html`**

```html
<!DOCTYPE html>
<html lang="zh-CN" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Spring Boot + Thymeleaf 首页</title>
    <style>
        body {
            margin: 0;
            font-family: "Microsoft YaHei", sans-serif;
            background: linear-gradient(135deg, #e0f2fe, #f0f9ff);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .card {
            background: #ffffff;
            width: 90%;
            max-width: 620px;
            border-radius: 16px;
            padding: 28px;
            box-shadow: 0 10px 30px rgba(14, 116, 144, 0.15);
            text-align: center;
        }
        h1 {
            color: #0f172a;
            margin-bottom: 12px;
        }
        p {
            color: #334155;
            line-height: 1.8;
            margin: 8px 0;
        }
        .tip {
            margin-top: 12px;
            color: #0369a1;
            font-weight: 600;
        }
    </style>
</head>
<body>
<div class="card">
    <h1>你好，Spring Boot！</h1>
    <p>你现在看到的是一个真实的 <code>index.html</code> 文件。</p>
    <p>它位于 <code>src/main/resources/templates</code> 目录中。</p>
    <p class="tip" th:text="${message}">页面动态提示消息</p>
</div>
</body>
</html>
```

---

## 5. 编写 Controller（返回视图名）

⚠️ 这里必须使用 `@Controller`，而不是 `@RestController`。

- `@Controller`：返回页面（视图）
- `@RestController`：返回 JSON 或纯文本

**File: `src/main/java/com/example/demo/HomeController.java`**

```java
package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 标记为控制器：用于返回页面视图
public class HomeController {

    @GetMapping("/") // 访问根路径时触发
    public String home(Model model) {
        // 向页面传递一个简单消息，供 Thymeleaf 展示
        model.addAttribute("message", "恭喜你，已经完成 Controller 到 HTML 的打通！");

        // 返回逻辑视图名：index（不写 .html）
        return "index";
    }
}
```

---

## 6. 启动类（Spring Boot 标准入口）

**File: `src/main/java/com/example/demo/Application.java`**

```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Spring Boot 启动注解：开启自动配置与组件扫描
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

---

## 7. 运行项目并访问页面

在项目根目录执行：

```bash
mvn spring-boot:run
```

启动成功后，浏览器访问：

- `http://localhost:8080`

如果你看到了带样式的欢迎卡片，说明你已经成功完成：

- Maven 依赖配置 ✅
- Controller 映射 ✅
- 真实 HTML 文件渲染 ✅

---

## 8. 原理解析（为什么返回 `"index"` 就能找到 HTML）

💡 你在 Controller 里返回的是**逻辑视图名**：`index`。

Thymeleaf 视图解析器会自动做这件事：

1. 给视图名加前缀：`classpath:/templates/`
2. 给视图名加后缀：`.html`
3. 拼成最终路径：`classpath:/templates/index.html`

所以 `return "index";` 最终会定位到：

- `src/main/resources/templates/index.html`

这就是模板引擎的价值：

- Controller 只关心“要显示哪个页面”
- HTML 专门负责页面结构和样式
- 避免在 Java 字符串里拼接大量 HTML，代码更清晰、可维护性更高

---

## 常见错误排查

### 1) 页面返回 404

- 检查 `@GetMapping("/")` 是否写对
- 检查是否使用了 `@Controller`（误写成 `@RestController` 会直接显示字符串）

### 2) 报错 `TemplateInputException`

- 检查 `index.html` 是否在 **`src/main/resources/templates`** 下
- 检查返回值是否为 `"index"`（不要写错文件名）

### 3) 启动失败提示 Java 版本不兼容

- Spring Boot 3.x 需要 JDK 17+
- 执行 `java -version` 再确认一次

### 4) 页面样式没有生效

- 本示例使用的是内联 `<style>`，正常不会丢失
- 如果你改成外部 CSS，需要放到 `src/main/resources/static` 并正确引用

---

如果你愿意，我下一步可以继续带你做一个“输入姓名并在页面欢迎你”的版本（仍然不涉及数据库，适合入门）。
