@echo off
REM Change directory to your project directory
cd C:\Users\disco\PycharmProjects\whl


REM Build the project with Maven
echo Building project with Maven...
./mvnw clean install

REM Check if Maven build was successful
@REM IF NOT %ERRORLEVEL% == 0 (
@REM     echo Maven build failed.
@REM     exit /b %ERRORLEVEL%
@REM )

REM Build Docker image. Replace "your-image-name" with the name you want for your Docker image.
echo Building Docker image...
docker build -t whlfiledownloader .

REM Check if Docker build was successful
IF NOT %ERRORLEVEL% == 0 (
    echo Docker build failed.
    exit /b %ERRORLEVEL%
)

REM Run Docker container. Replace "your-container-name" with the name you want for your Docker container.
echo Running Docker container...
docker run -p 8080:8080 whlfiledownloader


@REM REM Check if Docker run was successful
@REM IF NOT %ERRORLEVEL% == 0 (
@REM     echo Docker run failed.
@REM     exit /b %ERRORLEVEL%
@REM )

echo Script completed successfully.
