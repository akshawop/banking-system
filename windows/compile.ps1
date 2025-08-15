# compile.ps1 — Compile all Java files in src/me/akshawop/banking recursively

# Ensure bin directory exists
if (-not (Test-Path "bin")) {
    New-Item -ItemType Directory -Path "bin" | Out-Null
}

# Wait Message
Write-Host "Compiling the project. Please Wait..."

# Get all Java source files
$javaFiles = Get-ChildItem -Recurse -Path "src\me\akshawop\banking" -Filter *.java |
    ForEach-Object { $_.FullName }

# Compile them
javac -cp "lib/*" -d bin $javaFiles

Write-Host "Compilation complete. Class files saved to /bin"