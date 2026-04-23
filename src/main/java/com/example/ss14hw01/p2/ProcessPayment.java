package com.example.ss14hw01.p2;

import com.example.ss14hw01.p2.config.HibernateUtils;
import com.example.ss14hw01.p2.model.Order;
import com.example.ss14hw01.p2.model.Wallet;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ProcessPayment {
    public void processPayment(Long orderId, Long walletId, double totalAmount) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            // Bắt đầu transaction
            tx = session.beginTransaction();

            // 1. Cập nhật trạng thái đơn hàng
            Order order = session.get(Order.class, orderId);
            order.setStatus("PAID");
            session.update(order);

            // Giả lập lỗi hệ thống bất ngờ
            if (true) throw new RuntimeException("Kết nối đến cổng thanh toán thất bại!");

            // 2. Trừ tiền trong ví khách hàng
            Wallet wallet = session.get(Wallet.class, walletId);
            wallet.setBalance(wallet.getBalance() - totalAmount);
            session.update(wallet);

            // Nếu mọi thứ OK thì commit
            tx.commit();

        } catch (Exception e) {
            System.out.println("Lỗi hệ thống: " + e.getMessage());
            if (tx != null) {
                // Rollback để đảm bảo dữ liệu toàn vẹn
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }
}

