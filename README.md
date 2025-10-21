# HashLab

[![Java](https://img.shields.io/badge/Java-11+-red.svg)](#prerequisites)
[![Gradle](https://img.shields.io/badge/Gradle-7.x-blue.svg)](https://gradle.org/)
[![JavaFX](https://img.shields.io/badge/JavaFX-Enabled-ff69b4.svg)](https://openjfx.io/)
[![Build](https://img.shields.io/badge/Build-Gradle-success.svg)](#build--run)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

> **HashLab** is a Java-based application and research tool for studying the effectiveness and performance of different hashing algorithms and hash functions.  
> It provides an interactive JavaFX UI to configure experiments, run benchmarks (with timing normalization), and visualize results.

---

## 📚 Table of Contents

- [Purpose](#purpose)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Build & Run](#build--run)
- [Usage](#usage)
- [Data & Results](#data--results)
- [Extensibility](#extensibility)
- [Project Structure](#project-structure)
- [Testing](#testing)
- [Roadmap](#roadmap)
- [Known Bugs](#known-bugs)
- [License](#license)

---

## Purpose

The goal of **HashLab** is to enable analysis and comparison of multiple hashing algorithms and their hash functions.  
Users can execute standard hash-table operations (`put`, `get`, `delete`) on generated or imported datasets with known characteristics. Execution time is the primary metric; results are **normalized** via a calibration benchmark to account for hardware differences.  
Every experiment can be exported to CSV and visualized with charts for straightforward comparisons.

---

## Features

- **Multiple Hashing Algorithms**
  - Separate Chaining
  - Linear Probing
  - BST (Binary Search Tree) Hashing

- **Cryptographic Hash Functions**
  - MD5, SHA-1, SHA-256

- **Input Dataset Generation**
  - **Distributions:** uniform (random), normal (Gaussian), exponential  
  - **Configurable parameters:** size, mean/std (normal), lambda (exponential)

- **Import External Data**
  - Load datasets from text files (e.g., source files, configs, documents) for real-world analysis.

- **Benchmarking & Normalized Timing**
  - Built-in **calibration** to normalize timing results across machines.

- **Visualization**
  - Charts (via JFreeChart) for input distributions and performance results.
  - Results and configurations exported to **CSV** / **JSON**.

- **Modular & Extensible**
  - Registry pattern for algorithms and hash functions.
  - Add new algorithms/functions with minimal code changes.

- **Interactive UI (JavaFX)**
  - Choose algorithm(s), hash function(s), table size / chunk size.
  - Select operations (`put`, `get`, `delete`).
  - Generate or import datasets.
  - Tooltips, validation, and error messages for robust configs.

---

## Tech Stack

| Area        | Technology / Library | Notes                                                                  |
|-------------|----------------------|-------------------------------------------------------------------------|
| Language    | Java 11 (LTS)        | Required for compatibility and JavaFX support                           |
| UI          | JavaFX + ControlsFX  | Rich desktop UI and enhanced controls                                  |
| Build       | Gradle               | Build & dependency management                                           |
| Charts      | JFreeChart           | Visualize distributions and performance                                 |
| Tests       | JUnit 5              | Unit & integration tests                                                |
| JSON        | Gson                 | Serialize/deserialize configurations and results                        |
| VCS         | Git & GitHub         | Public repository and collaboration                                     |
| IDE         | IntelliJ IDEA        | Recommended; any Java IDE works                                         |

---

## Prerequisites

- **Java 11+** (JDK)  
- **Gradle 7.x** (optional if the project uses Gradle wrapper)  
- For JavaFX runtime: either use the Gradle JavaFX plugin or ensure platform-specific JavaFX modules are available.

> If you’re using the **Gradle Wrapper**, you don’t need a local Gradle installation.

---

## Installation

Clone the repository:

```bash
git clone https://github.com/Tokagero8/hashlab.git
cd hashlab
```

If you prefer SSH:
```bash
git clone git@github.com:Tokagero8/hashlab.git
cd hashlab
```

---

## Build & Run

Using Gradle Wrapper (recommended):

```bash
# On Unix/macOS
./gradlew clean build

# On Windows
gradlew.bat clean build
```

Run the JavaFX app:

```bash
# Unix/macOS
./gradlew run

# Windows
gradlew.bat run
```

>If your environment requires explicit JavaFX modules, make sure build.gradle includes the JavaFX plugin and the correct modules for your OS (e.g., javafx-controls, javafx-graphics).

---

## Usage

1. **Start the App**
   - From IDE or via terminal: `./gradlew run` (Windows: `gradlew.bat run`).
2. **Configure Experiment**
   - Pick **Hashing Algorithm(s)**: Separate Chaining, Linear Probing, BST Hashing.
   - Pick **Hash Function(s)**: MD5, SHA-1, SHA-256.
   - Set **Table Size**, **Chunk Size**, and operations: `put`, `get`, `delete`.
3. **Choose Dataset Source**
   - **Generate**: uniform / normal (μ, σ) / exponential (λ), with configurable size.
   - **Import**: load a text file (e.g., code/config/docs) as input dataset.
4. **Calibrate & Run**
   - Run calibration to **normalize timings** across machines.
   - Execute tests; UI shows validation messages, progress, and results.
5. **Analyze & Export**
   - Inspect **charts** (input distributions, performance per op/algorithm).
   - Export **CSV** (results) and **JSON** (configs) for further analysis.

---

## Data & Results

- **CSV Output** includes: algorithm, hash function, table size, data type, data size, chunk size, operation, efficiency result.
- **JSON** for saving/loading experiment setups.
- **Charts** (via JFreeChart) render input distributions and performance comparisons.

<details>
<summary>Example CSV schema</summary>

```csv
Algorithm,Function,Table Size,Data Type,Data Size,Chunk Size,Operation,Result
LinearProbingHash,MD5Hash,10000,Gaussian,5000,4,PUT,176.42
LinearProbingHash,MD5Hash,10000,Gaussian,5000,4,GET,163.94
LinearProbingHash,MD5Hash,10000,Gaussian,5000,4,DELETE,469.78
SeparateChainingHash,MD5Hash,10000,Gaussian,5000,4,PUT,225.25
SeparateChainingHash,MD5Hash,10000,Gaussian,5000,4,GET,232.20
SeparateChainingHash,MD5Hash,10000,Gaussian,5000,4,DELETE,253.86
BSTHash,MD5Hash,10000,Gaussian,5000,4,PUT,184.02
BSTHash,MD5Hash,10000,Gaussian,5000,4,GET,201.65
BSTHash,MD5Hash,10000,Gaussian,5000,4,DELETE,190.70
LinearProbingHash,MD5Hash,20000,Gaussian,10000,4,PUT,308.46
LinearProbingHash,MD5Hash,20000,Gaussian,10000,4,GET,356.44
LinearProbingHash,MD5Hash,20000,Gaussian,10000,4,DELETE,935.22
SeparateChainingHash,MD5Hash,20000,Gaussian,10000,4,PUT,423.92
SeparateChainingHash,MD5Hash,20000,Gaussian,10000,4,GET,446.64
SeparateChainingHash,MD5Hash,20000,Gaussian,10000,4,DELETE,433.17
BSTHash,MD5Hash,20000,Gaussian,10000,4,PUT,377.63
BSTHash,MD5Hash,20000,Gaussian,10000,4,GET,403.58
BSTHash,MD5Hash,20000,Gaussian,10000,4,DELETE,383.15
LinearProbingHash,MD5Hash,50000,Gaussian,25000,4,PUT,1124.74
LinearProbingHash,MD5Hash,50000,Gaussian,25000,4,GET,1043.53
LinearProbingHash,MD5Hash,50000,Gaussian,25000,4,DELETE,3089.93
SeparateChainingHash,MD5Hash,50000,Gaussian,25000,4,PUT,1165.31
SeparateChainingHash,MD5Hash,50000,Gaussian,25000,4,GET,1105.72
SeparateChainingHash,MD5Hash,50000,Gaussian,25000,4,DELETE,1120.34
BSTHash,MD5Hash,50000,Gaussian,25000,4,PUT,1011.62
BSTHash,MD5Hash,50000,Gaussian,25000,4,GET,1042.02
BSTHash,MD5Hash,50000,Gaussian,25000,4,DELETE,966.13
LinearProbingHash,MD5Hash,100000,Gaussian,50000,4,PUT,1964.70
LinearProbingHash,MD5Hash,100000,Gaussian,50000,4,GET,1840.52
LinearProbingHash,MD5Hash,100000,Gaussian,50000,4,DELETE,5364.42
SeparateChainingHash,MD5Hash,100000,Gaussian,50000,4,PUT,2275.60
SeparateChainingHash,MD5Hash,100000,Gaussian,50000,4,GET,2861.57
SeparateChainingHash,MD5Hash,100000,Gaussian,50000,4,DELETE,2982.47
BSTHash,MD5Hash,100000,Gaussian,50000,4,PUT,2087.32
BSTHash,MD5Hash,100000,Gaussian,50000,4,GET,2049.71
BSTHash,MD5Hash,100000,Gaussian,50000,4,DELETE,1978.02
LinearProbingHash,MD5Hash,200000,Gaussian,100000,4,PUT,4137.68
LinearProbingHash,MD5Hash,200000,Gaussian,100000,4,GET,3776.34
LinearProbingHash,MD5Hash,200000,Gaussian,100000,4,DELETE,10780.27
SeparateChainingHash,MD5Hash,200000,Gaussian,100000,4,PUT,6207.03
SeparateChainingHash,MD5Hash,200000,Gaussian,100000,4,GET,5501.20
SeparateChainingHash,MD5Hash,200000,Gaussian,100000,4,DELETE,5309.91
BSTHash,MD5Hash,200000,Gaussian,100000,4,PUT,4792.37
BSTHash,MD5Hash,200000,Gaussian,100000,4,GET,4688.76
BSTHash,MD5Hash,200000,Gaussian,100000,4,DELETE,4729.95
LinearProbingHash,MD5Hash,500000,Gaussian,250000,4,PUT,9644.23
LinearProbingHash,MD5Hash,500000,Gaussian,250000,4,GET,8828.36
LinearProbingHash,MD5Hash,500000,Gaussian,250000,4,DELETE,24254.42
SeparateChainingHash,MD5Hash,500000,Gaussian,250000,4,PUT,17429.03
SeparateChainingHash,MD5Hash,500000,Gaussian,250000,4,GET,15301.86
SeparateChainingHash,MD5Hash,500000,Gaussian,250000,4,DELETE,14386.50
BSTHash,MD5Hash,500000,Gaussian,250000,4,PUT,11327.00
BSTHash,MD5Hash,500000,Gaussian,250000,4,GET,11110.59
BSTHash,MD5Hash,500000,Gaussian,250000,4,DELETE,10845.95
```
</details>

---

## Extensibility
1. **Implement new HashFunction or HashTable variant.**
2. **Register via the project’s registry (e.g., AlgorithmRegistry.register(...)).**
3. **Add UI labels/tooltips and tests where relevant.**
>The modular architecture keeps new additions isolated from core infrastructure.

---

## Project Structure

```text
hashlab/
├─ build.gradle
├─ settings.gradle
├─ gradle/                    # Gradle wrapper
├─ src/
│  ├─ main/
│  │  ├─ java/...             # Algorithms, hash functions, registries, UI
│  │  └─ resources/...        # CSS, languages
│  └─ test/
│     └─ java/...             # JUnit 5 tests
└─ README.md
```

---

## Testing


```bash
# Unix/macOS
./gradlew test

# Windows
gradlew.bat test
```

Covers:

- **Hash function correctness**
- **Collision resolution (Separate Chaining / Linear Probing / BST)**
- **Calibration & normalization**
- **Dataset generation properties**

---

## Roadmap

- [ ] Additional hash functions (e.g., SHA-512, non-crypto variants like MurmurHash)
- [ ] More datasets (Zipfian, custom probability mass functions)
- [ ] Export charts as PNG/SVG
- [ ] CLI mode for headless benchmarking

---

- ## Known Bugs

During the build process (gradlew test or gradlew build), you might see error messages such as:

```bash
java.lang.IllegalStateException: Toolkit already initialized
```

This issue is related to JavaFX tests running in the build environment and **does not affect the actual application**.
You can safely ignore these test errors — the program will still run correctly using:

```bash
gradlew.bat run
```

You can also skip tests during build process by using:

```bash
# On Unix/macOS
./gradlew clean build -x test

# On Windows
gradlew.abt clean build -x test
```

---

## License

This project is licensed under the MIT License – see the [LICENSE](LICENSE) file for details.
