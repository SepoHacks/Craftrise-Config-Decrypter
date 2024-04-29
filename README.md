# Craftrise Config Decrypter

This script decrypts and parses the "%appdata%/.craftrise/config.json" file to retrieve the username and password.
## Authors

- [@sepohacks](https://www.github.com/sepohacks)


## Run And Use

#### 1. Clone or download the project:
####
```bash
  git clone https://github.com/SepoHacks/Craftrise-Config-Decrypter.git
```
#### 2. Open the code in IntelliJ IDEA or any other Java editor.
#### 3. Ensure necessary libraries are added:
- [google.code.gson](https://mvnrepository.com/artifact/com.google.code.gson/gson)
- [commons.codec](https://mvnrepository.com/artifact/commons-codec/commons-codec)
#### 4. Replace the location of the config file in the code.
- Decrypter.java
```bash
//...
    public Decrypter() {
        username = "empty";
        password = "empty";
        configPath = "./config.json"; // <-- Replace Me! (config.json file path)
    }
//...
```
#### 5. Compile and run the code.

## Screenshots

![App Screenshot](https://raw.githubusercontent.com/SepoHacks/Craftrise-Config-Decrypter/main/Screenshots/image.png)

