package com.ecom6.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecom6.VO.mem.MemberVO;
import com.ecom6.VO.notice.NoticeVO;
import com.ecom6.common.vo.PageVO;
import com.ecom6.service.mem.MemberService;
import com.ecom6.service.notice.NoticeService;

@RestController
public class RestApiController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	NoticeService noticeService;
	
	@PostMapping("idCheck")
	@ResponseBody
	public int idChekc(MemberVO mvo) {
		int cnt = 0;
			if (mvo.getMem_id()!=null) {
				cnt = memberService.idCheck(mvo.getMem_id());
			}
		return cnt;
	}
	
	@PostMapping("/api/getNotice")
	public List<NoticeVO> getNotice(NoticeVO nvo) {
		List<NoticeVO> noticeList = noticeService.getNotice(nvo);
		return noticeList;
	}

}
