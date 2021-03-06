package com.neusoft.bsp.info.controller;

import com.neusoft.bsp.common.base.R;
import com.neusoft.bsp.info.entity.Brand;
import com.neusoft.bsp.info.service.BrandService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/info")
@RestController
public class BrandController {
    @Resource
    private BrandService brandService;

    @RequestMapping(value = "/brand/get", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('admin', 'mvo')")
    public R getBrands(@RequestParam("manId") String manId) {
        return R.isSuccess().data(brandService.getAllBrands(manId));
    }

    @RequestMapping(value = "/brand/add", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('admin', 'mvo')")
    public R addBrand(@RequestBody Brand brand) {
        int result = brandService.addBrand(brand);

        if (result == -1) {
            return R.isFail().msg("fail to add");
        }

        Map<String, String> returnData = new HashMap<>();
        returnData.put("brdId", String.valueOf(result));
        return R.isSuccess().data(returnData);
    }

    @RequestMapping(value = "/brand/update", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('admin', 'mvo')")
    public R updateBrand(@RequestBody Brand brand) {
        int result = brandService.updateBrand(brand);

        if (result == 0) {
            return R.isFail().msg("fail to update");
        }

        return R.isSuccess();
    }

    @RequestMapping(value = "/brand/delete", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('admin', 'mvo')")
    public R deleteBrand(@RequestBody Brand brand) {
        int result = brandService.deleteBrand(brand.getBrdId());

        if (result == 0) {
            return R.isFail().msg("fail to delete");
        }

        return R.isSuccess();
    }

    @RequestMapping(value = "/brand/image/upload", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('admin', 'mvo')")
    public R uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("brdId") String brdId){
        if (file.isEmpty()) {
            return R.isFail().msg("please select image!");
        }

        String imagePath = brandService.uploadImage(file, brdId);
        if (imagePath == null) {
            return R.isFail().msg("upload image fail!");
        }

        return R.isSuccess().data(imagePath);
    }

    @RequestMapping(value = "/brand/getAllByUserId", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('admin', 'mvo')")
    public R getAllByUserId(@RequestParam("userId") String userId) {
        return R.isSuccess().data(brandService.getAllByUserId(userId));
    }
}