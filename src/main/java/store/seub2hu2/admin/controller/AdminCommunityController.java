package store.seub2hu2.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import store.seub2hu2.admin.dto.ReportDto;
import store.seub2hu2.admin.service.AdminService;
import store.seub2hu2.community.service.*;
import store.seub2hu2.community.vo.Marathon;
import store.seub2hu2.community.vo.Notice;
import store.seub2hu2.mypage.dto.AnswerDTO;
import store.seub2hu2.mypage.dto.QnaResponse;
import store.seub2hu2.mypage.service.QnaService;
import store.seub2hu2.security.user.LoginUser;
import store.seub2hu2.util.ListDto;
import store.seub2hu2.util.RequestParamsDto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/community")
@RequiredArgsConstructor
@Slf4j
public class AdminCommunityController {

    private final AdminService adminService;
    private final QnaService qnaService;
    private final MarathonService marathonService;
    private final NoticeService noticeService;
    private final ReportService reportService;

    @GetMapping("/qna")
    public String qna(@ModelAttribute RequestParamsDto dto , Model model, @AuthenticationPrincipal LoginUser loginUser) {

        ListDto<QnaResponse> qnaDto = qnaService.getQnas(dto,loginUser.getNo());

        model.addAttribute("qnaList", qnaDto.getData());
        model.addAttribute("pagination", qnaDto.getPaging());

        return "admin/community/qnalist";
    }

    @GetMapping("/qna/{qnaNo}")
    public String qnaDetailPage(@PathVariable("qnaNo") int qnaNo, Model model){

        QnaResponse qnaResponse = qnaService.getQnaByQnaNo(qnaNo);

        model.addAttribute("qna",qnaResponse);

        return "admin/community/answer-form";
    }

    @PostMapping("/qna/answer")
    public String qnaAnswerPage(@ModelAttribute AnswerDTO answerDTO, @AuthenticationPrincipal LoginUser loginUser){

        qnaService.updateAnswer(answerDTO, loginUser.getNo());

        return "redirect:/admin/community/qna";
    }

    @PostMapping("/updateReport")
    public String updateReport(@RequestParam("reportNo") int reportNo,
                               @RequestParam("reportType") String reportType,
                               Model model) {

        Map<String, Object> condition = new HashMap<>();
        condition.put("reportNo", reportNo);
        condition.put("reportType", reportType);

//        adminService.UpdateReport(condition);

        reportService.updateReport(reportType, reportNo);

        return "redirect:/admin/community/report";
    }

    @GetMapping("/report")
    public String report(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
                         @RequestParam(name = "rows", required = false, defaultValue = "10") int rows,
                         @RequestParam(name = "sort", required = false, defaultValue = "latest") String sort,
                         @RequestParam(name = "opt", required = false) String opt,
                         @RequestParam(name = "value", required = false) String value,
                         @RequestParam(name = "keyword", required = false) String keyword,
                         Model model) {

        Map<String, Object> condition = new HashMap<>();
        condition.put("page", page);
        condition.put("rows", rows);
        condition.put("sort", sort);

        if (StringUtils.hasText(value)) {
            condition.put("opt", opt);
            condition.put("keyword", keyword);
            condition.put("value", value);

        }

        ListDto<ReportDto> dto = adminService.getReport(condition);

        model.addAttribute("dto", dto.getData());
        model.addAttribute("paging", dto.getPaging());

        return "admin/community/reportlist";
    }

    @GetMapping("/community")
    public String community() {

        return "admin/community";
    }

    @GetMapping("/notice")
    public String notice(@ModelAttribute("dto") RequestParamsDto dto, Model model) {

        ListDto<Notice> nDto = noticeService.getNotices(dto);

        model.addAttribute("notices", nDto.getData());
        model.addAttribute("paging", nDto.getPaging());


        return "admin/community/notice";
    }

    @GetMapping("/marathon")
    public String marathon(@ModelAttribute("dto") RequestParamsDto dto, Model model) {

        ListDto<Marathon> mDto = marathonService.getMarathons(dto);

        model.addAttribute("marathons", mDto.getData());
        model.addAttribute("paging", mDto.getPaging());
        model.addAttribute("now", new Date());

        return "admin/community/marathon";
    }
}
