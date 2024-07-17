package com.ecom6.service.cart;
import com.ecom6.common.vo.PageInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom6.VO.cart.CartVO;
import com.ecom6.VO.order.OrderVO;
import com.ecom6.common.vo.PageVO;
import com.ecom6.dao.cart.CartDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartDao cartDao;

//	public Map<String, Object> getCartItemList(CartVO cvo, PageVO pgVo) { 
//		Map<String, Object> reSet = new HashMap<String, Object>(); 
//		int cnt = cartDao.getCartItemCnt();
//
//		//page 계산
//		if(pgVo.getCurBlock()<=0) pgVo.setCurBlock(1);
//		if(pgVo.getCurPg()<=0) pgVo.setCurPg(1);
//
//		int start = (pgVo.getCurPg()-1)*PageInfo.ROW_OF_PAGE+1;
//		int end = (pgVo.getCurPg()*PageInfo.ROW_OF_PAGE)>cnt?
//					cnt : pgVo.getCurPg()*PageInfo.ROW_OF_PAGE;
//		cvo.setStart(start);
//		cvo.setEnd(end);
//
//		//페이지 수 계산
//		int pgCnt = (cnt%PageInfo.ROW_OF_PAGE==0)?
//					cnt/PageInfo.ROW_OF_PAGE:
//					cnt/PageInfo.ROW_OF_PAGE+1;
//		pgVo.setPgCnt(pgCnt);
//		//페이지 블록 계산
//		int blockCnt = (pgCnt%PageInfo.PAGE_OF_BLOCK==0)?
//				       pgCnt/PageInfo.PAGE_OF_BLOCK:
//				       pgCnt/PageInfo.PAGE_OF_BLOCK+1;
//
//		pgVo.setBlockCnt(blockCnt);
//		//startPg
//		int startPg = (pgVo.getCurBlock()-1)*PageInfo.PAGE_OF_BLOCK+1;
//		//endPg
//		int endPg = pgVo.getCurBlock()*PageInfo.PAGE_OF_BLOCK>pgCnt?
//				    pgCnt:pgVo.getCurBlock()*PageInfo.PAGE_OF_BLOCK;
//		pgVo.setStartPage(startPg);
//		pgVo.setEndPage(endPg);
//		
//		List<CartVO> cartList = cartDao.getCartItemList(cvo);
//		reSet.put("pgvo", pgVo);
//		reSet.put("cartTot", cnt);
//		reSet.put("cartList", cartList);
//		return reSet;
//	}
	@Override
	public Map<String, Object> getCartItemList(String mem_id) { 

		 Map<String, Object> reSet = new HashMap<String, Object>(); 
		 int cnt = cartDao.getCartItemCnt(mem_id);
		 int total = 0;
		 if (cnt != 0) {
			 total = cartDao.getCartTotalPrcie(mem_id);
		 }
		 // 페이지 계산로직 
		 List<OrderVO> cartList = cartDao.getCartItemList(mem_id);
		 log.info("total ======> " +total);
		 reSet.put("subTotal", total);
		 reSet.put("cartTot", cnt);
		 reSet.put("cartList", cartList);

		 return reSet; 
	 }


	@Override
	public Boolean findCart(CartVO cvo) {

		return cartDao.findCart(cvo);
	}
	
	@Override
	public void addCart(CartVO cvo) {
		log.info("cvo111 ======> "+cvo);
		//int p_no = cvo.getP_no();
		int quantity = cvo.getQuantity();
		if (cvo.getStock()<quantity) {
			cvo.setQuantity(quantity);
		}
		log.info("cvo222 ======> "+cvo);
		int cnt = cartDao.countCart(cvo);
		if (cnt==0) {
			log.info("cvo444 ======> "+cvo);			
			cartDao.insertCart(cvo);
		}
		else {
			log.info("cvo333 ======> "+cvo);	
			cartDao.updateCart(cvo);
		}
	}

	@Override
	public void updateCartItem(CartVO cvo) {
		cartDao.updateCartItem(cvo);
	}

	@Override
	public void deleteCartItem(CartVO cvo) {
		cartDao.deleteCartItem(cvo);
	}
	
	@Override
	public void deleteCart(ArrayList<CartVO> cartList) {
		String memId = cartList.get(0).getMem_id();
		cartDao.deleteCart(memId);
	}






//	@Override
//	public List<CartVO> addCart(CartVO cvo) {
//		
//		int p_no = cvo.getP_no();
//		int quantity = cvo.getC_quantity();
//		if(cvo.containsKey(p_no)) {			
//			CartVO temp = (CartVO) CartList.get(p_no);
//			quantity += temp.getC_quantity();
//			if(temp.getStock()<quantity) {
//				// 메세지 없이 최대 수량으로 수정
//				temp.setC_quantity(temp.getStock());
//			} else {
//				temp.setC_quantity(quantity);
//			}
//			CartList.put(p_no, temp);
//		} else {
//			CartList.put(p_no, cvo);			
//		}
//		log.info("CartList ===> : "+CartList.get(p_no));
//		return (List<CartVO>) CartList;
//		
//		
//	}




//	@Override
//	public Hashtable<Integer, OrderVO> addCart(OrderVO ovo) {
//		// 상품을 추가하는데 시존 장바구니에 있으면 수량 추가
//		int p_no = ovo.getP_no();
//		int quantity = ovo.getQuantity();
//		if(hCartList.containsKey(p_no)) {			
//			OrderVO temp = (OrderVO) hCartList.get(p_no);
//			quantity += temp.getQuantity();
//			if(temp.getStock()<quantity) {
//				// 메세지 없이 최대 수량으로 수정
//				temp.setQuantity(temp.getStock());
//			} else {
//				temp.setQuantity(quantity);
//			}
//			hCartList.put(p_no, temp);
//		} else {
//			hCartList.put(p_no, ovo);			
//		}
//		//log.info("hCartList ===> : "+hCartList.get(p_no));
//		return hCartList;
//		
//		
//	}
//
//	@Override
//	public Hashtable<Integer, OrderVO> updateCart(OrderVO ovo) {
//		// 상품을 추가하는데 시존 장바구니에 있으면 수량 추가
//		int p_no = ovo.getP_no();
//		int quantity = ovo.getQuantity();
//		if(hCartList.containsKey(p_no)) {			
//			OrderVO temp = (OrderVO) hCartList.get(p_no);		
//			if(temp.getStock()<quantity) {
//				// 메세지 없이 최대 수량으로 수정
//				temp.setQuantity(temp.getStock());
//			} else {
//				temp.setQuantity(quantity);
//			}
//			hCartList.put(p_no, temp);
//		} 
//		return hCartList;
//	}
//
//	@Override
//	public Hashtable<Integer, OrderVO> deleteCart(OrderVO ovo) {
//		hCartList.remove(ovo.getP_no());
//		return hCartList;
//	}
//
//	@Override
//	public Hashtable<Integer, OrderVO> getCartList() {
//		return hCartList;
//	}


}








//	@Override
//	public List<CartVO> addCart(CartVO cvo) {
//		
//		int p_no = cvo.getP_no();
//		int quantity = cvo.getC_quantity();
//		if(cvo.containsKey(p_no)) {			
//			CartVO temp = (CartVO) CartList.get(p_no);
//			quantity += temp.getC_quantity();
//			if(temp.getStock()<quantity) {
//				// 메세지 없이 최대 수량으로 수정
//				temp.setC_quantity(temp.getStock());
//			} else {
//				temp.setC_quantity(quantity);
//			}
//			CartList.put(p_no, temp);
//		} else {
//			CartList.put(p_no, cvo);			
//		}
//		log.info("CartList ===> : "+CartList.get(p_no));
//		return (List<CartVO>) CartList;
//		
//		
//	}


	

//	@Override
//	public Hashtable<Integer, OrderVO> addCart(OrderVO ovo) {
//		// 상품을 추가하는데 시존 장바구니에 있으면 수량 추가
//		int p_no = ovo.getP_no();
//		int quantity = ovo.getQuantity();
//		if(hCartList.containsKey(p_no)) {			
//			OrderVO temp = (OrderVO) hCartList.get(p_no);
//			quantity += temp.getQuantity();
//			if(temp.getStock()<quantity) {
//				// 메세지 없이 최대 수량으로 수정
//				temp.setQuantity(temp.getStock());
//			} else {
//				temp.setQuantity(quantity);
//			}
//			hCartList.put(p_no, temp);
//		} else {
//			hCartList.put(p_no, ovo);			
//		}
//		//log.info("hCartList ===> : "+hCartList.get(p_no));
//		return hCartList;
//		
//		
//	}
//
//	@Override
//	public Hashtable<Integer, OrderVO> updateCart(OrderVO ovo) {
//		// 상품을 추가하는데 시존 장바구니에 있으면 수량 추가
//		int p_no = ovo.getP_no();
//		int quantity = ovo.getQuantity();
//		if(hCartList.containsKey(p_no)) {			
//			OrderVO temp = (OrderVO) hCartList.get(p_no);		
//			if(temp.getStock()<quantity) {
//				// 메세지 없이 최대 수량으로 수정
//				temp.setQuantity(temp.getStock());
//			} else {
//				temp.setQuantity(quantity);
//			}
//			hCartList.put(p_no, temp);
//		} 
//		return hCartList;
//	}
//
//	@Override
//	public Hashtable<Integer, OrderVO> deleteCart(OrderVO ovo) {
//		hCartList.remove(ovo.getP_no());
//		return hCartList;
//	}
//
//	@Override
//	public Hashtable<Integer, OrderVO> getCartList() {
//		return hCartList;
//	}
	
	

