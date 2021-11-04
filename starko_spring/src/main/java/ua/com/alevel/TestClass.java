package ua.com.alevel;

import ua.com.alevel.annotations.Autowired;
import ua.com.alevel.annotations.Service;

@Service
public class TestClass{
    @Autowired
    private DiContext diContext;

    public void test() {
        System.out.println("diContext = " + diContext);
    }
}
