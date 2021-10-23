package com.example.userservice.client;

public class aa {
    public static void main(String[] args) {
        try {
            for(int i=0;i<7;i++) {
                if(i==2) throw new Exception("에러발생");
                System.out.println("값 나와라 =>"+i);
            }
        } catch (Exception e) {
            System.out.println("밖에서 정지됨");
        }
        try {
            for(int i=0;i<7;i++) {
                try {
                    if(i==2) throw new Exception("에러발생");
                    System.out.println("값 나와라 =>"+i);
                } catch (Exception e) {
                    System.out.println("안에서 정지됨");
                }
            }
        } catch (Exception e) {
            System.out.println("밖에서 정지됨");
        }
    }
}
