package ch1;

public class TvMain {
    public static void main(String[] args) {
        // tv 객체 생성 (불러서 사용하기 위해) / 객체 생성 안해도 되는것 : static 붙은 것은 제외 => 많이 사용되진 않음

        // 방법 1
        // LgTv lgTv = new LgTv();
        // SamsungTv samsungTv = new SamsungTv();

        // 방법 2 => 자주 사용(수정할 때 LgTv -> SamsungTv 로만 바꾸면 되기 때문에 수정을 조금이라도 덜 하는 것을 선호)
        LgTv tv = new LgTv();
        // Tv tv = new LgTv(new BritzSpeaker());
        tv.setSpeaker(new BritzSpeaker()); // setSpeaker는 자식클래스에 생성했기 때문에 부모클래스인 Tv 객체를 생성하면 오류가 뜸

        tv.powerOn();
        // NullPointerException (BritzSpeaker 객체생성을 하지 않아서 오류가 뜸)
        tv.volumeUp();
        tv.volumeDown();
        tv.powerOff();

    }

}
