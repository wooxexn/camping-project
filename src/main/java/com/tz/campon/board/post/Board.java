package com.tz.campon.board.post;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
public class Board {
        String m_id;
        String m_pw;
        String m_name;
        String m_tel;
        String m_birthday;
        int m_point;
        String m_grade;

        public Board(String m_id, String m_pw, String m_name, String m_tel, String m_birthday, int m_point,
                      String m_grade) {
                super();
                this.m_id = m_id;
                this.m_pw = m_pw;
                this.m_name = m_name;
                this.m_tel = m_tel;
                this.m_birthday = m_birthday;
                this.m_point = m_point;
                this.m_grade = m_grade;
        }
    }
