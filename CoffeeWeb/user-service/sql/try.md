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

(2) 삽입, 수정 시 null인 Entity 필드가 있을 경우
- @DynamicInsert, @DynamicUpdate를 적용하게 되면 null인 값은 제외하고
쿼리문을 날려준다.  
- @PrePersist, @PreUpdate를 적용하게 되면 쿼리 날리기전에,
null로 날리지 않게 값을 정의하는 방식으로 사용할 수 있다.  
  
=> 나는 DB 테이블에 컬럼 생성 시, default 값을 설정 해뒀기 때문에
  null인 값 제외를 선택했다.

(3) spring boot 사용 시, 스프링부트의 SpringPhysicalNamingStrategy가 기본으로 바꿔주는 설정입니다.
- 카멜 케이스 -> 언더스코어  (memberPoint -> member_point)
- .(점) -> _(언더스코어)
- 대문자 -> 소문자

3. 회원가입, 로그인 암호화 방식 사용

(1) BCryptPasswordEncoder  
- Bcrypt알고리즘으로 암호화 => 랜덤 salt를 부여하여 여러번 Hash를 적용한 암호화 방식
- 복호화가 불가능한 단방향 알고리즘이다. 현재 사용되는 해시 알고리즘 중에 가장 강력한 암호화 방식  
- SHA알고리즘은 동일한 평문일 경우 해시값이 같지만, Bcrypt는 동일한 평문도 해시값이 다름 ex)비번1234를 두번 Bcrypt사용 시 해시값이 다른다.  
- 해시에 의해 암호화된 데이터를 다이제스트(digest)라고 부른다.
- 반복횟수와 salt를 이용한 것인데, 반복회수는 new BCryptPasswordEncoder(반복횟수 입력 가능 n) 로 해서,
암호화된 값을 또 암호화 시키는 것을 의미한다. 즉 n번 암호화해서 만들어낸다. 그러나 반복횟수도 결국 해커가 
  부르트포스를 사용하면 얻을 수 있다. 소금을 친다는의미로 salt를 부여한다. 즉 임의의 문자열을 추가로 붙인다.
  그래서 최대한 무력화 시키기 위한 방법인 것이다.

(2) http.csrf().disable();

(3) spring security는 기본적으로 X-Fram-Option Click jacking 공격 막기 설정이 되어있다.
=> iframe 사용하기 때문에 설정 해주어야한다.  
a. http.headers().frameOptions().disable(); //X-Frame-OPtions를 비활성화하는 설정이지만 보안이슈가 있다.  
b. http.headers().frameOptions().sameOrigin(); //이걸 사용하면 된다, 동일 도메인에서 iframe 접근이 가능하도록 X-Frame-OPtions을 sameOrigin 한 것이다.  
c. properties에서 security.headers.frame=false 로 설정해도 된다.  

4. 기능 : 로그인 만들기, 별명 변경

5. test해보기(Junit)  

a. 기초  
(1) @Mock : 스프링빈에 등록이 안되는 객체
- 필드명에 @Mock을 선언해서 에러검증을 쉽게, 해당 필드가 Mock객체임을 명확하게 한다.  
- Service레이어 테스트시, Repository를 가짜 객체로 만드는 용도로 사용 가능하다.  
- 스프링 컨테이너가 DI를 하는 방식이 아니라 객체생성시 생성자 Mock객체를 직접 주입한다.  
- 생성자 주입을 사용해야 편하게 사용이 가능하다.  
- 스프링을 띄우지 않으므로 MockBean을 사용할때보다 빠르다.  

(2) @MockBean : 가짜 Bean을 스프링에 등록해달라는 의미
- 스프링 컨테이너가 기존에 갖고있는 Bean객체는 MockBean객체로 치환되어 DI에 사용된다.

b. 단위테스트  
(1) Repository
- @DataJpaTest를 클래스 위에 붙여서 사용하면 됨 => 그러나 spring data JPA를 쓰는 것이 아닌 내가 만든 repository면 @Autowirde가 안된다.    
- 그러므로 만든 Repository일 경우에, Repository는 어차피 내부에 로직이 거의 없고, 기능 대부분을 EntityManager에 의존하기 때문에, @SpringBootTest를 그냥 사용하면 된다.  


