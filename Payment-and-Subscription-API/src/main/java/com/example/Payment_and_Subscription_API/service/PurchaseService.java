package com.example.Payment_and_Subscription_API.service;

import com.example.Payment_and_Subscription_API.entity.Ebook;
import com.example.Payment_and_Subscription_API.entity.Purchase;
import com.example.Payment_and_Subscription_API.entity.User;
import com.example.Payment_and_Subscription_API.repo.EbookRepository;
import com.example.Payment_and_Subscription_API.repo.PurchaseRepository;
import com.example.Payment_and_Subscription_API.repo.UserRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private EbookRepository ebookRepository;

    @Autowired
    private UserRepository userRepository;

    // Method to handle the purchase of an e-book
    public Purchase purchaseEbook(Long userId, Long ebookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ebook ebook = ebookRepository.findById(ebookId)
                .orElseThrow(() -> new RuntimeException("Ebook not found"));

        Purchase purchase = Purchase.builder()
                .user(user)
                .ebook(ebook)
                .amountPaid(ebook.getPrice())
                .purchaseDate(LocalDateTime.now().toString())
                .build();

        return purchaseRepository.save(purchase);
    }
}
