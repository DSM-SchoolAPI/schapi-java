# schapi-java
SchoolAPI Java용 라이브러리

## Guide
아래는 **2018년 4월 3일 대덕소프트웨어마이스터고등학교 조식**을 출력합니다.

```
class Main {
    public static void main(String[] args) throws IOException {
        SchoolAPI api = new SchoolAPI(SchoolAPI.Region.DAEJEON, "G100000170", SchoolAPI.Type.HIGH);

        System.out.println(api.getMonthlyMenus(2018, 4).get(3).breakfast);
    }
}
```
