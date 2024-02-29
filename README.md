# Uno Game - SYSC 3110 Project (Milestone three)

## Table of Contents
1. [Introduction](#introduction)
2. [Technologies](#technologies)
3. [Installation](#installation)
4. [Usage](#usage)
5. [Testing](#testing)
6. [New Features](#new-features)

## Introduction
This project is a simplified implementation of the Uno card game, designed as part of the SYSC 3110 course. The game is designed for 2 to 4 players, including the option to play against AI opponents. It features a lightweight game engine where only one player wins, and their points are calculated and displayed at the end of the game. A new flip method has been introduced to switch between classic and new game modes.

## Technologies
The project is written in Java and uses the following libraries:
- `java.util` for data structures like ArrayList, List, and Scanner.
- `java.util.Random` for generating random numbers.
- `JUnit` for unit testing.

## Installation
To set up the project, follow these steps:
1. Ensure that all project files are in a single folder.
2. Open the project folder in IntelliJ IDEA.
3. Locate the `UnoGame` class.

### Dependencies
- Java 8 or above
- IntelliJ IDEA (or any other Java-supported IDE)

## Usage
To run the game:
1. Open IntelliJ IDEA and navigate to the `UnoGame` class.
2. Run the main method within the `UnoGame` class.
3. The game will start, and you can follow on-screen instructions to play.
4. Use the flip method to switch between classic and new game modes.
5. Choose to play against human players, AI players, or a mix of both.

## Testing
To run the test cases:
1. Each test class can be found in the test directory.
2. Right-click on each test class and select Run 'TestName'.
3. Feel free to run individual test cases or all of them at once.

## New Features
- **Flip Method**: This new feature allows players to switch between the classic Uno game mode and an alternate mode, providing a versatile gaming experience.
- **AI Players**: The game now supports AI players. Players can choose to compete against AI opponents, making the game challenging and fun for solo players or when additional human players are not available.
