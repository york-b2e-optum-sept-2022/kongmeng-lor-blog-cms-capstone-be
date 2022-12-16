TO RUN DOCKERCOMPOSE:

    1. FrontEnd
        - Download kongmeng-lor-blog-cms-capstone-fe
        - In the terminal, write : docker build -t frontend ./
        - It will create an image of frontend / port is localhost:3000

    2. Backend
        - Then download this project, kongmeng-lor-blog-cms-capstone-be
        - In the terminal, write: docker build -t backend ./ 
        - Then it will create an backend image

    3. Dockercompose
        - In the terminal, cd Dockercompose
        - In the terminal, docker compose up