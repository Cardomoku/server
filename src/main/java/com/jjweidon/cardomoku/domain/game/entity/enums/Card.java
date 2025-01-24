package com.jjweidon.cardomoku.domain.game.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Card {

    // 숫자 카드 (2~10)
    SPADE_2_1(1, "spade", "2", 1), SPADE_2_2(2, "spade", "2", 2),
    SPADE_3_1(3, "spade", "3", 1), SPADE_3_2(4, "spade", "3", 2),
    SPADE_4_1(5, "spade", "4", 1), SPADE_4_2(6, "spade", "4", 2),
    SPADE_5_1(7, "spade", "5", 1), SPADE_5_2(8, "spade", "5", 2),
    SPADE_6_1(9, "spade", "6", 1), SPADE_6_2(10, "spade", "6", 2),
    SPADE_7_1(11, "spade", "7", 1), SPADE_7_2(12, "spade", "7", 2),
    SPADE_8_1(13, "spade", "8", 1), SPADE_8_2(14, "spade", "8", 2),
    SPADE_9_1(15, "spade", "9", 1), SPADE_9_2(16, "spade", "9", 2),
    SPADE_10_1(17, "spade", "10", 1), SPADE_10_2(18, "spade", "10", 2),

    HEART_2_1(19, "heart", "2", 1), HEART_2_2(20, "heart", "2", 2),
    HEART_3_1(21, "heart", "3", 1), HEART_3_2(22, "heart", "3", 2),
    HEART_4_1(23, "heart", "4", 1), HEART_4_2(24, "heart", "4", 2),
    HEART_5_1(25, "heart", "5", 1), HEART_5_2(26, "heart", "5", 2),
    HEART_6_1(27, "heart", "6", 1), HEART_6_2(28, "heart", "6", 2),
    HEART_7_1(29, "heart", "7", 1), HEART_7_2(30, "heart", "7", 2),
    HEART_8_1(31, "heart", "8", 1), HEART_8_2(32, "heart", "8", 2),
    HEART_9_1(33, "heart", "9", 1), HEART_9_2(34, "heart", "9", 2),
    HEART_10_1(35, "heart", "10", 1), HEART_10_2(36, "heart", "10", 2),

    DIAMOND_2_1(37, "diamond", "2", 1), DIAMOND_2_2(38, "diamond", "2", 2),
    DIAMOND_3_1(39, "diamond", "3", 1), DIAMOND_3_2(40, "diamond", "3", 2),
    DIAMOND_4_1(41, "diamond", "4", 1), DIAMOND_4_2(42, "diamond", "4", 2),
    DIAMOND_5_1(43, "diamond", "5", 1), DIAMOND_5_2(44, "diamond", "5", 2),
    DIAMOND_6_1(45, "diamond", "6", 1), DIAMOND_6_2(46, "diamond", "6", 2),
    DIAMOND_7_1(47, "diamond", "7", 1), DIAMOND_7_2(48, "diamond", "7", 2),
    DIAMOND_8_1(49, "diamond", "8", 1), DIAMOND_8_2(50, "diamond", "8", 2),
    DIAMOND_9_1(51, "diamond", "9", 1), DIAMOND_9_2(52, "diamond", "9", 2),
    DIAMOND_10_1(53, "diamond", "10", 1), DIAMOND_10_2(54, "diamond", "10", 2),

    CLUB_2_1(55, "club", "2", 1), CLUB_2_2(56, "club", "2", 2),
    CLUB_3_1(57, "club", "3", 1), CLUB_3_2(58, "club", "3", 2),
    CLUB_4_1(59, "club", "4", 1), CLUB_4_2(60, "club", "4", 2),
    CLUB_5_1(61, "club", "5", 1), CLUB_5_2(62, "club", "5", 2),
    CLUB_6_1(63, "club", "6", 1), CLUB_6_2(64, "club", "6", 2),
    CLUB_7_1(65, "club", "7", 1), CLUB_7_2(66, "club", "7", 2),
    CLUB_8_1(67, "club", "8", 1), CLUB_8_2(68, "club", "8", 2),
    CLUB_9_1(69, "club", "9", 1), CLUB_9_2(70, "club", "9", 2),
    CLUB_10_1(71, "club", "10", 1), CLUB_10_2(72, "club", "10", 2),

    // Q 카드
    SPADE_Q_1(73, "spade", "q", 1), SPADE_Q_2(74, "spade", "q", 2),
    HEART_Q_1(75, "heart", "q", 1), HEART_Q_2(76, "heart", "q", 2),
    DIAMOND_Q_1(77, "diamond", "q", 1), DIAMOND_Q_2(78, "diamond", "q", 2),
    CLUB_Q_1(79, "club", "q", 1), CLUB_Q_2(80, "club", "q", 2),

    // K 카드
    SPADE_K_1(81, "spade", "k", 1), SPADE_K_2(82, "spade", "k", 2),
    HEART_K_1(83, "heart", "k", 1), HEART_K_2(84, "heart", "k", 2),
    DIAMOND_K_1(85, "diamond", "k", 1), DIAMOND_K_2(86, "diamond", "k", 2),
    CLUB_K_1(87, "club", "k", 1), CLUB_K_2(88, "club", "k", 2),

    // A 카드
    SPADE_A_1(89, "spade", "a", 1), SPADE_A_2(90, "spade", "a", 2),
    HEART_A_1(91, "heart", "a", 1), HEART_A_2(92, "heart", "a", 2),
    DIAMOND_A_1(93, "diamond", "a", 1), DIAMOND_A_2(94, "diamond", "a", 2),
    CLUB_A_1(95, "club", "a", 1), CLUB_A_2(96, "club", "a", 2),

    // O 카드
    O_1(97, "o", "o", 1), O_2(98, "o", "o", 2),
    O_3(99, "o", "o", 3), O_4(100, "o", "o", 4),

    // J 카드
    SPADE_J_1(101, "spade", "j", 1), SPADE_J_2(102, "spade", "j", 2),
    HEART_J_1(103, "heart", "j", 1), HEART_J_2(104, "heart", "j", 2),
    DIAMOND_J_1(105, "diamond", "j", 1), DIAMOND_J_2(106, "diamond", "j", 2),
    CLUB_J_1(107, "club", "j", 1), CLUB_J_2(108, "club", "j", 2);

    private final int id;
    private final String suit;
    private final String rank;
    private final int version;
}