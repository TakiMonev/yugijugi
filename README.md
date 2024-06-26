# yugijugi_sang_youn

여기저기라는 프로그램으로 각종 엔터테이먼트 시설들의 정보를 소개하는 웹사이트입니다.

## 1. 기획 내용

- 목적 : 지역별 / 테마별 핫플을 안내해주는 사이트
- 목표 :
    - 게시판 : 생성, 삭제
    - 게시글 : 생성, 수정, 삭제
    - 댓글 : 생성, 수정, 삭제
    - ERD :
    
 ![image](https://github.com/TakiMonev/yugijugi/assets/61460821/1e260155-bd01-43c8-88d0-5598fd2f4bc5)

    ## 2. 트러블 슈팅
    
    ### 2 - 1) th:onclick이 되지 않는 현상
    
    **문제 : th:onclick="location.href='/theme/${theme.id}'"이런식으로 onclick에 문자열 값이 들어갈 경우 ${} 안의 값을 인식 하지 못함**
    
    **원인 :** **Thymeleaf에서는 태그 속성 값에 있는 문자열 내에서 자바스크립트 표현식인 ${}에 접근할 수 없다. 따라서 ${} 안의 값에 접근하기 위해서는 Thymeleaf의 인라인 표현식 구문을 사용해야 한다.**
    
    **해결 방법 : th:onclick="|location.href='/theme/${theme.id}'|"와 같이 ‘|’를 이용한다**
    
![image](https://github.com/TakiMonev/yugijugi/assets/61460821/f9a036e9-04e4-4dad-987f-3856e7396c79)

    ### 2 - 2) Controller에서 JSON을 못가져오는 현상
    
    **문제 :** **JS에서는 JSON에 접근하기 쉬웠는데 Java에서는 어떻게 접근해야 할지 몰랐다.**
    
    **해결 방법 : 객체를 정의하고 그 객체를 @RequestBody로 받아온다.**
    
![image](https://github.com/TakiMonev/yugijugi/assets/61460821/df7d4d1e-daf9-487d-b5d1-1d3711990367)

    
    ## 3. 경험 공유
    
    **소감**
    
    **프로젝트를 하면서 계획을 엉성하게 짜고 들어가니까 연속적으로 꼬이는 현상이 발생하였다. 예를 들어 Comment 객체 같은 경우에는 password를 가지고 있어야 (로그인 기능을 구현하지 않았으므로) 댓글의 주인을 판별해서 주인만이 댓글을 삭제할 수 있도록 구현 가능한데 그 부분을 놓쳐서 삭제 부분을 제대로 구현하지 못했다.**
    
    **또 프론트 부분에서 시간을 많이 잡아 먹혔는데 그 바람에 백엔드 쪽에 시간을 할애 못한 것이 너무 아쉬웠다. 사실 댓글 수정 부분도 했어야 했는데** **진행이 어느 정도 됬다고 생각하고 후반부에 좀 덜 빡세게 하여 이 부분을 완성하지 못한 것은 너무나도 아쉽다. 다만 이번 일을 계기로 프론트 실력도 어느 정도 향상 되었으니 다음 번엔 더 잘할 수 있을 것같다.**
    
    **긍정적인 경험으로는 프론트와 백을 둘 다 단기간에 경험하고 한 프로젝트를 만드는 것이 자의적으로는 굉장히 힘든데 어쨌거나 주어진 시간 내에 완성해야 하다보니 적당한 압박감도 있었고 재미와 스릴도 있었던 것 같다.**
    
    **요약**
    
    1. **프로젝트를 하면서 계획을 잘 짜자**
    2. **프론트 부분에서 시간이 오래 걸렸는데 앞으로는 더 잘할 수 있을 것 같다.**
    3. **프론트와 백 둘 다 정해진 기간 내에 하다보니 굉장히 유익했다.**
    
