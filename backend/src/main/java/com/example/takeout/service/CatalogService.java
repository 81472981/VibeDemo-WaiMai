package com.example.takeout.service;

import com.example.takeout.model.MenuItem;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {
    private final List<MenuItem> menu = List.of(
            new MenuItem(1, "招牌牛肉饭", "慢炖牛腩、温泉蛋和时蔬，米饭软糯入味。", "主食", new BigDecimal("29.90"), "https://images.unsplash.com/photo-1590301157890-4810ed352733?auto=format&fit=crop&w=900&q=80", 2380, true),
            new MenuItem(2, "藤椒鸡腿饭", "去骨鸡腿配藤椒汁，清爽微麻，适合午餐。", "主食", new BigDecimal("26.90"), "https://images.unsplash.com/photo-1603133872878-684f208fb84b?auto=format&fit=crop&w=900&q=80", 1854, true),
            new MenuItem(3, "番茄肥牛面", "酸甜番茄汤底，肥牛片和手工面一碗满足。", "面食", new BigDecimal("31.90"), "https://images.unsplash.com/photo-1569718212165-3a8278d5f624?auto=format&fit=crop&w=900&q=80", 1287, false),
            new MenuItem(4, "鲜虾云吞汤", "现包云吞，虾仁弹牙，汤底清亮。", "小吃", new BigDecimal("18.80"), "https://images.unsplash.com/photo-1547592166-23ac45744acd?auto=format&fit=crop&w=900&q=80", 935, false),
            new MenuItem(5, "烤鸡凯撒沙拉", "低脂烤鸡胸、罗马生菜和帕玛森奶酪。", "轻食", new BigDecimal("24.80"), "https://images.unsplash.com/photo-1546793665-c74683f339c1?auto=format&fit=crop&w=900&q=80", 764, false),
            new MenuItem(6, "杨枝甘露", "芒果、西柚和椰奶，冰爽解腻。", "饮品", new BigDecimal("15.80"), "https://images.unsplash.com/photo-1627308595229-7830a5c91f9f?auto=format&fit=crop&w=900&q=80", 1521, true),
            new MenuItem(7, "冰淇淋", "奶香浓郁，口感绵密，饭后来一份刚刚好。", "甜品", new BigDecimal("12.80"), "https://images.unsplash.com/photo-1563805042-7684c019e1cb?auto=format&fit=crop&w=900&q=80", 846, false),
            new MenuItem(8, "解辣老冰棍", "清凉爽口，甜度轻，搭配辣味主食很解腻。", "甜品", new BigDecimal("5.80"), "https://images.unsplash.com/photo-1586769412527-ab0855979b2e?auto=format&fit=crop&w=900&q=80", 1326, true)
    );

    public List<MenuItem> listMenu() {
        return menu;
    }

    public Optional<MenuItem> findById(long id) {
        return menu.stream().filter(item -> item.id() == id).findFirst();
    }
}
