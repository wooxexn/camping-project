package com.tz.campon.reservation.controller;

import com.tz.campon.reservation.DTO.CampDetail;
import com.tz.campon.reservation.DTO.CampList;
import com.tz.campon.reservation.DTO.Reservation;
import com.tz.campon.reservation.Repository.CampDetailRepository;
import com.tz.campon.reservation.Repository.CampListRepository;
import com.tz.campon.reservation.Repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class ReservationController {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    CampListRepository campListRepository;

    @Autowired
    CampDetailRepository campDetailRepository;


    @GetMapping("/reserveInfo")
    public String getReservation(@RequestParam(name = "user_id", defaultValue = "rlaanrnd") String user_id,
                                 Model model){

        List<Reservation> list = reservationRepository.getReservationById(user_id);

        System.out.println( list);

        model.addAttribute("reservelist", list);

        return "reserveInfo";
    }

    @Transactional
    @PostMapping("/addReserve")
    public String addReserve(@ModelAttribute Reservation reservation, Model model){

        try {
            // 예약 정보 삽입
            reservationRepository.register(reservation);
            // 성공 후 메시지 추가
            model.addAttribute("message", "예약이 성공적으로 추가되었습니다.");
            return "redirect:/reserveInfo";
        } catch (Exception e) {
            model.addAttribute("message", "예약 추가에 실패했습니다.");
            System.out.println("예약실패");
            return "redirect:/campdetail";
        }


    }

    @GetMapping("/reserve")
    public String getCampDetail(@RequestParam (name = "camp_id") int camp_id,
                                @RequestParam(name = "user_id", defaultValue = "rlaanrnd") String user_id,
                                Model model){

        List<CampDetail> list = campDetailRepository.getCampDetail(camp_id);
        CampList campList = campListRepository.getCampgroundById(camp_id);
        List<Reservation> reservationList = reservationRepository.getReservationById(user_id);

        System.out.println(list);

        model.addAttribute("campDetailList", list);
        model.addAttribute("camplist2", campList);
        model.addAttribute("reservation", reservationList);


        return "reserve";
    }

}