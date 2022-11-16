1. msa서비스로 연결 시킴  


2. dto, entity 분리

(1) 생성자  
setter는 가급적 사용하지 않는다 어디서든지 값 변경이 되니깐 위험  
=> 생성자에서 미리 만들자!  
=> 그러나 RequiredArgsConstructor, AllArgsConstructor같은 경우에는 모든 사용하는 것들에대한
  생성자를 넣어줘야하고 필드 순서를 잘 못 넣으면 문제가 생기므로 좋지 않다.
  그리고 어디든지 public으로 열려있다.  
=> @NoArgsConstructor(access = AccessLevel.PROTECTED)를 사용해서
  범위를 막고, @Builder를 사용해서 필요한 값만 받아서 사용할 수 있고, 필드 순서에대한 실수도 방지가 된다.

(2) @DynamicInsert) 과 두번쨰 방법 (@PrePersist)  



   
3. 기능 : 로그인 만들기, 별명 변경  


4. test해보기(Junit)