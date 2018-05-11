package org.seckill.web;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatSeckillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/seckill")
public class SeckillController {

    public static final Logger logger = LoggerFactory.getLogger(SeckillController.class);
    @Autowired
    private SeckillService service;
    @RequestMapping("/list")
    public String list(Model model){
        List<Seckill> seckills = service.getSeckillList();
        model.addAttribute("seckills",seckills);

        return "list";
    }
    @RequestMapping("/{seckillId}/details")
    public String getSeckill(@PathVariable("seckillId") Long seckillId ,Model model){
        if (seckillId == null){
            return "redirect:/seckill/list";
        }
        Seckill seckill = service.getSeckill(seckillId);
        if (seckill == null){
            return "redirect:/seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return "details";
    }
    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> getNowTime(Model model){
        long nowTime = new Date().getTime();
        return new SeckillResult<Long>(true,nowTime);
    }
    @RequestMapping(value = "/{seckillId}/exposer",method = RequestMethod.POST,
                        produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){

       SeckillResult<Exposer> result = null;
//       if(exposer.isExposed()){
//           result = new SeckillResult<Exposer>(true,exposer);
//       }else {
//           result = new SeckillResult<Exposer>(false,"没开启或结束");
//       }
        try{
            Exposer exposer = service.ExportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true,exposer);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            result = new SeckillResult<Exposer>(false,e.getMessage());
        }
        return result;
    }
    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExcution> excution(@PathVariable("seckillId")Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "userPhone",required = false) Long userPhone){
        SeckillResult<SeckillExcution> result = null;
//        userPhone=17763238627l;
        if(userPhone == null){

            result = new SeckillResult<SeckillExcution>(false,"未注册");
        }else {
            try{
                //System.out.println(service);
                SeckillExcution excution = service.ExcuteSeckill(seckillId,userPhone,md5);
                //System.out.println(excution);
                result = new SeckillResult<SeckillExcution>(true,excution);
            }catch (SeckillCloseException e){
                result = new SeckillResult<SeckillExcution>(false,e.getMessage());
            }catch (RepeatSeckillException e){
                result = new SeckillResult<SeckillExcution>(false,e.getMessage());
            }catch (Exception e){

                result = new SeckillResult<SeckillExcution>(false,e.getMessage());
            }
        }

        return result;
    }
}
