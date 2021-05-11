package com.ssafy.thxstore.reservation.domain;

public enum ReservationStatus {
    /**
     * 주문 승락 누르기 전, 수문 수락 누른 후, 수령 대기 , 수령완료
     */
    DEFAULT, ACCEPT, STAND_BY, FINISH
}
