package com.tz.campon.reservation.controller;

import com.tz.campon.reservation.DTO.CampDetail;
import com.tz.campon.reservation.DTO.CampList;
import com.tz.campon.reservation.DTO.Reservation;
import com.tz.campon.reservation.Repository.CampDetailRepository;
import com.tz.campon.reservation.Repository.CampListRepository;
import com.tz.campon.reservation.Repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import static java.time.temporal.ChronoUnit.DAYS;


@Controller
public class ReservationController {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    CampListRepository campListRepository;

    @Autowired
    CampDetailRepository campDetailRepository;


    @GetMapping("/reserveInfo")
    public String getReservation(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        List<Reservation> list = reservationRepository.getReservationById(userId);

        System.out.println( list);

        model.addAttribute("reservelist", list);

        return "reservation/reserveInfo";
    }

    @Transactional
    @PostMapping("/addReserve")
    public String addReserve(@ModelAttribute Reservation reservation, Model model){
        System.out.println(reservation);
        try {
            // 예약 정보 삽입
            reservationRepository.register(reservation);
            // 성공 후 메시지 추가
            model.addAttribute("message", "예약이 성공적으로 추가되었습니다.");
            return "redirect:/main";
        } catch (Exception e) {
            model.addAttribute("message", "예약 추가에 실패했습니다.");
            System.out.println(e);
            System.out.println("예약실패");
            return "redirect:/main";
        }


    }

    @GetMapping("/reserve")
    public String getCampDetail(@RequestParam (name = "camp_id") int camp_id,
                                @RequestParam(name="check_in_date")  String  check_in_date,
                                @RequestParam(name="check_out_date") String   check_out_date,
                                @RequestParam (name = "campdetail_id") int campdetail_id,
                                Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        CampDetail campDetailOne = campDetailRepository.selectCampDetailOne(camp_id, campdetail_id);
        CampList campList = campListRepository.getCampgroundById(camp_id);
        List<Reservation> reservationList = reservationRepository.getReservationById(userId);

        System.out.println(campDetailOne);

        LocalDate start = LocalDate.parse(check_in_date);
        LocalDate end = LocalDate.parse(check_out_date);

        model.addAttribute("campDetailOne", campDetailOne);
        model.addAttribute("camplist2", campList);
        model.addAttribute("reservationList", reservationList);
        model.addAttribute("check_in_date",check_in_date);
        model.addAttribute("check_out_date",check_out_date);
        model.addAttribute("days",DAYS.between(start, end));
        model.addAttribute("campdetail_id",campdetail_id);
        model.addAttribute("user_id", userId);


        return "reservation/reserve";
    }

}
