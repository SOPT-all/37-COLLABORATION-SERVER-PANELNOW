package com._37collaborationserver.domain.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com._37collaborationserver.domain.product.entity.Product;
import com._37collaborationserver.domain.product.entity.UserProduct;
import com._37collaborationserver.domain.product.repository.ProductRepository;
import com._37collaborationserver.domain.product.repository.UserProductRepository;
import com._37collaborationserver.domain.user.entity.User;
import com._37collaborationserver.domain.user.service.UserService;
import com._37collaborationserver.global.exception.BadRequestException;
import com._37collaborationserver.global.exception.NotFoundException;
import com._37collaborationserver.global.exception.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
	private final ProductRepository productRepository;
	private final UserProductRepository userProductRepository;
	private final UserService userService;

	@Transactional
	public void changePoint(final Long userId, final Long productId) {
		User user = userService.getUserById(userId);
		Product product = getProductById(productId);

		if (user.getCurrentPoint() < product.getPrice()){
			throw new BadRequestException(ErrorCode.POINT_NOT_ENOUGH);
		}
		user.updatePoint(product.getPrice());
		UserProduct userProduct = new UserProduct(user, product);
		userProductRepository.save(userProduct);
	}

	public Product getProductById(final Long productId) {
		return productRepository.findById(productId)
			.orElseThrow(()-> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
	}
}
