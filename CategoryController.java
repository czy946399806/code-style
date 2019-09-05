package com.xuhui.tbk_manager_server.controller;

import com.xuhui.tbk_manager_server.bean.Category;
import com.xuhui.tbk_manager_server.service.CategoryService;
import com.xuhui.tbk_manager_server.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public Map<String,Object> Category(@PathVariable(name = "id") Long id,@RequestParam(name = "token" ,required = true)String token){
        Map<String,Object> map = new HashMap<>();
        if(redisService.checkToken(token)){
            map.put("login",true);
            if(id!=0){
                //查id
                Category category=categoryService.category(id);
                map.put("res",category);
            }else{
                //查所有
                List<Category> cl= categoryService.categorys();
                map.put("res",cl);
            }
        }else{
            map.put("login",false);
        }
        return map;
    }
    @GetMapping("/children/{pid}")
    public Map<String ,Object > categoryChildren(@PathVariable(name = "pid") Long pid,@RequestParam(name = "token" ,required = true)String token){
        Map<String,Object> map = new HashMap<>();
        if(redisService.checkToken(token)){
            List<Category> list=categoryService.categoryChildren(pid);
            map.put("res",list);
            map.put("login",true);
        }else{
            map.put("login",false);
        }
        return map;
    }

    @PostMapping()
    public Map<String,Object> addCategory(Category category,@RequestParam(name = "token" ,required = true)String token){
        Map<String,Object> map = new HashMap<>();
        if(redisService.checkToken(token)){
            map.put("login",true);
            Category category1 = categoryService.addCategory(category);
            map.put("success",category1!=null?true:false);
        }else{
            map.put("login",false);
            return map;
        }
        return map;
    }
    @PutMapping()
    public Map<String,Object> updateCategory(Category category,@RequestParam(name = "token" ,required = true)String token){
        Map<String,Object> map = new HashMap<>();
        if(redisService.checkToken(token)){
            map.put("login",true);
            map.put("success",categoryService.updateCategory(category));
        }else{
            map.put("login",false);
            return map;
        }
        return map;
    }

    @DeleteMapping("/{id}")
    public Map<String,Object> deleteCategory(@PathVariable(name = "id")Long id,@RequestParam(name = "token" ,required = true)String token){
        Map<String,Object> map = new HashMap<>();
        if(redisService.checkToken(token)){
            map.put("login",true);
            map.put("success",categoryService.deleteCategory(id));
        }else{
            map.put("login",false);
            return map;
        }
        return map;
    }
}
