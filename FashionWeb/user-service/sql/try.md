1. msa서비스로 연결 시킴  


2. 기능  

A. user-service  
(1) 회원가입   
(2) 로그인 만들기  
(3) 사용자 정보 보기  
(4) 닉네임 변경
   
B. order-service  
(1) 상품 목록  
(2) 상품 주문  
(3) 주문 내역 조회  

c. seller-service  
(1) 회원가입   
(2) 로그인 만들기  
(3) 고객 주문 목록  
(4) 고객 주문 확인(ORDER => READY)로 변경  
(5) 상품 등록  
(6) 판매자가 판매하는 상품 목록  

3. dto, entity 분리

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

(4) ModelMapper 사용 시, @setter가 entity에 없다면 변환 안되서 null로 찍히는 문제 발생  
- ModelMapper 클래스 생성할 때 아래의 set정보를 넣어주면 private값에대해서도 @Setter없이도 mapper가 적용된다.  
.setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
.setFieldMatchingEnabled(true);

(5) ModelMapper를 쓰는 것의 장점과 단점  
- [장점]  
  => 간편하다. 코드량이 줄어든다.
- [단점]  
  => 모델이 단순하지 않을 경우 오히려 복잡해진다. ex) 연관관계  
  => 수동으로는 오류를 잡을 수 없고 무조건 실행해야 오류(런타임 에러)를 발견할 수 있다. (수동으로 만들면 entity <-> dto 변환 시 컴파일 시 오류(컴파일타임 에러) 발견하기 좋다.)  
  => ModelMapper는 동시성 성능 이슈가 있다. 수천 TPS의 리엑티브 모델에서는 이 부분이 명확하게 병목으로 나왔다. (수천 TPS가 안되는 상황에서는 상관없음.)

(6) builder 패턴 사용하고, JPA Update값(수정)이 있을시 수정이 안되는 문제 발생  
-  더티체크를 위해서 setter를 사용하지 않고, changeXxx 등 메소드를 만들어서 엔티티 내부의 필드 값을 변경하면, 데티체크가 일어나고, 실제 UPDATE 쿼리를 실행시킬 수 있다.
   => setter를 되도록 사용하지 말자는거지 아예 쓰지 말라는 것은 아님!! 유연하게 사용하자.  
-  @Builder(toBuilder = true) 이런식으로 사용하고 save해도 되는데 이는 더티체크가 아닌 새로운 엔티티를 생성하는 것이다.

4. 회원가입, 로그인 암호화 방식 사용

(1) BCryptPasswordEncoder  
- Bcrypt알고리즘으로 암호화 => 랜덤 salt를 부여하여 여러번 Hash를 적용한 암호화 방식
- 복호화가 불가능한 단방향 알고리즘이다. 현재 사용되는 해시 알고리즘 중에 가장 강력한 암호화 방식  
- SHA알고리즘은 동일한 평문일 경우 해시값이 같지만, Bcrypt는 동일한 평문도 해시값이 다름 ex)비번1234를 두번 Bcrypt사용 시 해시값이 다르다.  
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

5. TDD_test해보기(Junit)  

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
- @DataJpaTest를 클래스 위에 붙여서 사용하면 됨 => 그러나 spring data JPA를 쓰는 것이 아닌 내가 만든 repository면 @Autowierd가 안된다.    
- 그러므로 만든 Repository일 경우에, Repository는 어차피 내부에 로직이 거의 없고, 기능 대부분을 EntityManager에 의존하기 때문에, @SpringBootTest를 그냥 사용하면 된다.  
  
(2) Service  
- 단위테스트에서는 Repository클래스는 @Mock을 사용한다.  
- @Mock이 붙은 목객체(repository)를 @InjectMocks(service)이 붙은 객체에 주입시킬 수 있다.
- 레이어 테스트 시, Dto&Entity 테스트 방법  
=> when ~ thenReturn으로 Repository결과값을 처리한다고하더라도, entity의 결과값을 뱉어낸다.  
=> 그러나 service의 return값은 dto이다.  
=> 그래서 우리가 사이에 mapper를 사용하는데, 이 mapper역시 when ~ thenReturn으로 결과값 작성을 해야한다.
- ModelMapper의 when~thenReturn 시, return값이 List라면 배열을 나눠서 return해야한다.  
=> ex)  
   List<Item>  mi = makeItems();  
   when(orderRepository.findAll()).thenReturn(mi);  
   when(mapper.map(eq(mi.get(0)),eq(ItemDto.class))).thenReturn(makeItemsDto().get(0));  
   when(mapper.map(eq(mi.get(1)),eq(ItemDto.class))).thenReturn(makeItemsDto().get(1));  

(3) Controller  
- @WebMvcTest(컨트롤러 클래스.class)를 사용  
=> 해당 클래스만 실제로 로드하여 테스트를 해준다.  
=> (컨트롤러 클래스.class)를 넣지 않으면, 모든 Controller어노테이션이 들어간 클래스들이 전부 로드 된다.  
- MockMvc클래스 사용 => 컨트롤러의 api를 테스트하는 용도이다.  
- Service클래스는 @MockBean을 사용한다.  
=> WebMvcTest어노테이션으로 테스트하면, 스프링이 로드하긴한다. 따라서, 컨트롤러 안의 service클래스는 @MockBean으로 가짜 빈을 스프링컨테이너에 넣어서 DI에 사용한다.
- service가 void return type일 때, Exception Test 해야할 경우(given 넣는 법)  
=> willThrow(에러 클래스).given(서비스 객체).void메소드();
  

(4) when ~ thenReturn(Mockito) vs given ~ willReturn(BDDMockito)   
- BDDMockito가 제공하는 기능과 Mockito가 제공하는 기능은 별반 다르지 않다.(BDDMockito의 코드를 살펴보면 Mockito을 상속한 클래스)    
- BDDMockito의 메소드 명을 보면 given ~ willReturn에서 볼 수 있듯이 Given, When, Then 구조에 더 잘 맞춰진 것이다. 메소드명으로 Given인지 알아보기가 쉽다.  
=> when ~ thenReturn이 given에 있으면 마치 When단계에 있어야하는 거처럼 보인다.  
- 이 외로도 BDD 기본 패턴의 then에서 사용되는 Mockito에서 제공하는 verify() 도 then().should() 로 대체될 수 있다.  

6. JPA

- cascade = CascadeType.ALL  
=> 상위 엔티티가 하위 엔티티를 가지고 있으면 작업 전파  
=> ex) 주문(상위 엔티티)하면 주문 아이템(하위 엔티티)도 같이 전부 저장(insert) => 따로 주문 아이템을 insert 안해도 된다.  

7. 스프링부트랑 리액트 연결하기
- 결과값 보낼 때 공통의 Result<T> 클래스를 만들어서 code, msg를 맞춰 보낸다. => 공통적인 규격으로 깔끔하다.
