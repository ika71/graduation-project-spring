# 전자제품 리뷰사이트 
어드민은 전자제품과 평가할 수 있는 항목을 등록하고  
사용자들은 그 전자제품에 평점 등록과 리뷰(게시판 CRUD, 이미지 업로드, 댓글 구현)를 작성할 수 있는 웹사이트 입니다.  
Spring boot, JPA, query dsl, Spring Security를 이용해 REST API 웹 서버를 구축하였으며    
프론트 엔드는 Next.js로 구현하였습니다.  
프론트엔드의 소스코드는 <a href="https://github.com/ika71/graduation-project-next" target="_blank">https://github.com/ika71/graduation-project-next</a> 에 있습니다.  
Azure cloud 가상 머신과 docker-compose, docker hub를 이용하여 클라우드 배포를 하였고  
배포된 사이트는 <a href="http://20.39.188.135" target="_blank">http://20.39.188.135</a> 에서 확인 하실 수 있습니다.  
어드민 계정(id = admin@admin.com, password = admin@)  
일반 계정(id = test@test.com, password = test@)  

# ERD
![ERD](/readme/ERD.png)

# 웹사이트 화면
![그림1](/readme/그림1.png)
![그림2](/readme/그림2.png)
![그림3](/readme/그림3.png)