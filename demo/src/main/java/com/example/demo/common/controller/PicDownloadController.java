package com.example.demo.common.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @auther chenyong
 * @date 2020/6/28 14:13
 */
@Controller
@RequestMapping(value="/picDownload/")
public class PicDownloadController {


    /**
     * 根据图片地址下载图片并打包成zip
     * @param request
     * @param response
     */
    @RequestMapping(value = "zipPics",method = RequestMethod.GET)
    public void downloadZipPics(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String[] picUrlsArr={
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/V3/9afbb9a8b5b1454f8be29927f4dcdffa.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/V3/a3df0db626914be78c5d2c240ffb721a.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/V3/a4b498f3db5745a4b3000cbb203fcd99.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/V3/9563755bc52f4a4694debd8f2cdca877.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200526/V3/02ace0d3d1e94401bbec44a7828e6d8b.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200526/V3/0d74637e685044c596bffb4761bdf80f.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200523/V3/ba26b72500f045ff8f463d738fc74f97.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200520/V3/91d03082749a4bdf8ef8c43ebc3e2621.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200519/V3/3fe9367266a34b5bb700e50011254533.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200519/V3/415151c78cc7451ba7d678cc46a4559f.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200519/V3/8825436d93074e26b4b24ee05a478292.jpg",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-2eb6d5bb27a64c3a893d8269b959922d-g-o-0.png",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-7e02c41314e94423b8a8ad86b3df7174-g-o-0.png",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-1c5d21a55caf4a2daeec921f5d3e1ca4-g-o-0.png",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-728fdaf109924f60b7bed59222947c75-g-o-0.png",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-5478c523c5834a7682fc878e6ea6a3a1-g-o-0.png",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-d9e7248dd1c84e6f894fff52d9b26766-g-o-0.png",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-d08a5dbf4ec04fbbbffc35874f88562c-g-o-0.jpg",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-4fffdf5b19a3441a9a6ed690e559e385-g-o-0.jpg",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-cfd042908f9f4c0c9c67039dae18d04c-g-o-0.png",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-3fc5af2566544e52a5f5d760215fa553-g-o-0.png",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-a86d0c47adc1459597cd985562f34138-g-o-0.jpg",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-805b6a6767b44892b6b328ee856f458c-g-o-0.jpg",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-39762ee5157943acbce19cac321def27-g-o-0.jpg",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-d1251b87b048478086b421f3e2e18dbd-g-o-0.jpg",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/201907/1/7-6a448eb6b31342438a2129a9cac2ce4e-g-o-0.png",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-720ddf25061241b781f26a48cdbf15bd-g-o-0.png",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-f7edea2dbc54485fbf199c2eff7d6b95-g-o-0.png",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-b9526d4a3b73439e8d5a8c61f8a14076-g-o-0.png",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-08b61af649f5444095f874e13adb739a-g-o-0.png",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-4bd84d8239e74df1bb0d2147cce27c94-g-o-0.png",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-161b6f4e1a8140f4a6f7b1e7371068d0-g-o-0.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200422/V3/8696327b5c244e4b9f66f8ad75dd686b.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200422/V3/358d46a616284447a899f92d7d58cf36.jpg",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-e42f24d0bbc044ef95a7b184f4475ad8-g-o-0.jpg",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-f162feb346ee460eb3982fe3bc002cd2-g-o-0.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200421/V3/5534828c99624f81bb7ba8c2e81cab49.jpg",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-e60d91a694154b9f9680d826fa4bcee1-g-o-0.jpg",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-dcf01bf38b2048cebeb7e5163ae32403-g-o-0.jpg",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-b2d4388a2c0c4c43bc29b57fcd1f7e9b-g-o-0.png",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-471ec8c30990424ebc38ff902d11c4d7-g-o-0.jpg",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-0d2cffa3817b4964bc0e3cd029ea8ff0-g-o-0.jpg",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-c8b3566d70454f77bd74ccd6e7f61045-g-o-0.jpg",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-c431179de629454bb45deccfc2639d7d-g-o-0.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/V3/c243bec99f5b4a638e78c8652908a5f7.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/V3/ecfd1e7d095b46169c684e7db058dc5a.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/V3/4e4b6d56be62430a80544f8f564f7d06.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200416/V3/25bea516e43c4f228f784c81a413f430.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200416/V3/af0cfafd377f41ecbb331c297da268fa.jpg",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-6b231565ea0b4b6f86a3c7f66d8e41e9-g-o-0.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200416/V3/8784ebba5bb8481291f8b9563fca5298.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200416/V3/b006630f1bbc402596601029854bf475.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200416/V3/899bad4071c14a32a63cfafb64c41cb0.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200416/V3/80f197f0535e40e494ae317fc4eaafcf.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200415/V3/4059e6be120345e2b3376321e5a37708.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200415/V3/190345a0e26143b8a11772d63bddf4a6.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200415/V3/0cfefe535f704ff8bffd09de44b870bf.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200415/V3/9a6d8372eab34afbb6a9de87e48fe313.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200415/V3/a19663c54d70430093ad68003e1d72e4.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200415/V3/a4ac9821584e4d91878240e5829e0e7a.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200415/V3/f30244b55625463a9d40fc854ca9c4d8.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200415/V3/bc11e3bac57f46f3b2061ed336000369.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200415/V3/8e7cf52dc7424758b750ef38051e2d18.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200415/V3/6bf894f7a4404deab76d1d909a7e5711.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200415/V3/a692cd66f8d7404d853bdb7a0a20a1cb.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200415/V3/0fa8782d6f1748359567482c6e95c55e.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200415/V3/a1cfb6625c664d87882d3f4aa6fc4e93.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200415/V3/a813737246694f549b267f6150f224aa.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200415/V3/d871990076264d73bec2f8dbd8a66dcb.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200415/V3/0fa81f382ce3411a866e049916150ba6.png",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202004/302/7-a3e8f1a8e58f41568c01629cbb08e412-g-o-0.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200414/V3/de94e2001ad148da9ad02164f8932e34.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200414/V3/df670efae29a43eab25dd91b612cdac9.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200414/V3/d8ec5909c2e748a6afd235d5fcbf917e.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200414/V3/bd90c02e2eed4ae68785a1bd6f9c40b9.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200414/V3/3a8fe3898b58413cb5099ea4c408e74f.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200414/V3/bb16c95b861549d0854ac6e0f3dab77b.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200414/V3/f418995dbeae4e10be7f8faf9592aa9b.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200414/V3/56cdaf06f8a843779a4540437c70e405.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200414/V3/c2b6d03e324247acac6bb00a72866746.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200414/V3/2147600f26c84a019d41bb5b5e3319e4.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200414/V3/24298ef81c5e496f9fc6e5dc52538298.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200414/V3/26739eba18504d9ab7b188c30fc6fe67.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200414/V3/d3711ac72dff423281c58bee103d17c5.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200414/V3/2e39311f9d044f62b3066349e74514b1.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200414/V3/443999400a8c4e3db95402cce11ec93f.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200408/V3/5fd34c2e3e2a4bbbbfe4d2682fae92ef.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200408/V3/cf9e44c319b9429db14758396bb64a41.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200408/V3/ff2cd7b010a447ec81b6e06b8db50f48.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200408/V3/cd8a47f730184483942a704d45323091.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200408/V3/85a0ebcf520b4834b98df99b139fefc4.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200408/V3/f6deecbaa9794622837fa4a5062c2ad8.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200408/V3/5e744be801c541dbb20f8db757169374.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200404/V3/26ce5870664b4690b4f2a8c13b9b7a30.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200404/V3/bacdb11360c94a09b4dcfd83eabed0b5.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200404/V3/e50079d958b94f7c9234d3af7c5bee1c.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200404/V3/5b37ab82544947809bed21444d6a0a5a.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/87eabcfab97c449a99fafafd4deee3fc.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/68dd45ca7753478d9260ee633284e5d1.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/0e19a78e763b42fc93df80991493a916.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/d8037be6c64e48228e8e7e5743ad318d.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/5521abde6b70487cb7396db92a59d73d.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/1e4ac0e48e7a4db0b6d74e8375654bb3.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/3f3dea7bdf844358b6522270f26121a3.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/d354daa5b9f94ac0bcead1f87bb3d242.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/805d4f0f5ce4473687ccd8e305bf3d45.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/02640012a5e44e918b455334e369fd6e.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/c8e11b56d943497c8df151c7987eeb8e.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/070a39bab64c4d04a45ee53415f82702.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/9ec783daea774ac89d963881075b5311.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/9945d10c36d64807a0d3d5bca9042ea8.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/710db8aa0f18460493790d0edfb78e9a.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/94ec769ea59c422dbf5701444c357bc2.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/1bf093a7838042c6a02987033c1a3573.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/1ac7b920c15a475fb56a894a1b3b8dcc.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/045c85f9a9914682a64508a07a3335c2.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/ba0a3630afae4f00b8870f986001f287.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/108f1548d45c45c6b7ff8bf1236dc6b7.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/49ecadae86bf46efae1d14e5f561001d.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/abf30878dfe243a983fe3d8f914eab21.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200403/V3/a473174bba9a4ce4a0d2975cf505b8f6.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200402/V3/655844d51b1a42e5965c3aca80c29ad7.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200402/V3/7178857c73f84df9a819735c6310d2af.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200402/V3/43ff8aee82564e79a3ae5658d47edd90.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200402/V3/0ab2065e96ed4577b92a793b06b0344a.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200402/V3/e31f589ddd494fadbdb8508d2ec5a4b3.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200402/V3/2409017142dd4a0abe521fa39d73eb12.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200402/V3/f026e30cdcca4d50887b85051d9fda76.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200402/V3/29cb74c245504c8c8292b848a91bc614.png",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200402/V3/78a0d4c1b0524d16ae548a52b569cb62.png",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202003/302/4-d73f43b054164554b9426689d301268b-g-o-0.jpg",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202003/302/4-e49da07ceef2450786e615082827bfb0-g-o-0.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200329/V3/fdfcf5af07a54d28853c57b8f8c9ffdb.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200329/V3/f2636addd52248a1ba0bf1eb323ed62f.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200329/V3/b732c4cce02746bea829bed7437df734.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200329/V3/daf047763c7a411e88cba1a87fec0f5b.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200329/V3/5e9e8f7043c0409dacac063feafcb371.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200329/V3/935e3c3128c04cc2822128e86898c817.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200329/V3/c7336446657b484b831f45ee4b2daf16.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200329/V3/620595a6232f45009838c71e7f9100f8.jpg",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202003/302/4-c237669ebcc64fc4a894c314c2cf3bad-g-o-0.jpg",
                "http://udfstest.10101111.com/ucarudfs/resource/V2/202003/302/4-a229294121394da1bc38f25ada4fe6cf-g-o-0.jpg",
                "http://udfstest.10101111.com/ucarudfs/udfs/visit/ampots/g/20200328/V3/73be83884bfd4b008c51ac09f63c21bf.jpg"

        };
        System.out.println("总共图片有："+picUrlsArr.length+"张");
        String zipName="CY_zip";
        byte[] data=getPicsBytes(picUrlsArr);
        System.out.println("总共图片大小为："+data.length/1024/1024+"M");
        //清楚首部空白行
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + zipName + ".zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        //输出解压包
        IOUtils.write(data, response.getOutputStream());
        //关闭流而不用抛异常
        IOUtils.closeQuietly(response.getOutputStream());
    }

    private byte[] getPicsBytes(String[] picUrls) throws Exception{
        ByteArrayOutputStream arrayOutputStream=new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream=new ZipOutputStream(arrayOutputStream);
        for(int i=0;i<picUrls.length;i++){
            System.out.println("第"+(i+1)+"张图片开始下载");
            String picName=picUrls[i].substring(picUrls[i].lastIndexOf("/")+1);
            downloadPics(picUrls[i],picName,zipOutputStream);
        }
        IOUtils.closeQuietly(zipOutputStream);
        return arrayOutputStream.toByteArray();
    }

    private void downloadPics(String picUrl,String picName,ZipOutputStream zipOutputStream) throws Exception{
        String[] picsSuffixArr={"jpg","png","gif","jpeg","bmp"};
        List<String> picsSuffixList= Arrays.asList(picsSuffixArr);
        String picSuffix=picUrl.substring(picUrl.lastIndexOf(".")+1);
        if(!StringUtils.isEmpty(picSuffix)){
            BufferedInputStream in=null;
            if(!picsSuffixList.contains(picSuffix)){
               throw new Exception("图片格式有误，无法下载");
            }
            try{
                URL url = new URL(picUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5 * 1000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty(
                        "Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, "
                                + "application/x-shockwave-flash, application/xaml+xml, "
                                + "application/vnd.ms-xpsdocument, application/x-ms-xbap, "
                                + "application/x-ms-application, application/vnd.ms-excel, "
                                + "application/vnd.ms-powerpoint, application/msword, */*");
                conn.setRequestProperty("Accept-Language", "zh-CN");
                conn.setRequestProperty("Charset", "UTF-8");
                InputStream inStream = conn.getInputStream();
                if(inStream == null) {
                    throw new Exception("获取压缩的数据项失败! 图片文件名为：" + picName);
                }else {
                    in = new BufferedInputStream(inStream);
                }

                // 压缩条目不是具体独立的文件，而是压缩包文件列表中的列表项，称为条目，就像索引一样
                //ZipEntry zipEntry = new ZipEntry("文件夹/" + imageName);

                ZipEntry zipEntry = new ZipEntry("CK"+File.separator +picName);
                // 定位到该压缩条目位置，开始写入文件到压缩包中
                zipOutputStream.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024 * 10]; // 读写缓冲区(每张图片限定10M以内)
                int read = 0;
                while ((read = in.read(bytes)) != -1) {
                    zipOutputStream.write(bytes, 0, read);
                }
                IOUtils.closeQuietly(inStream); // 关掉输入流
                IOUtils.closeQuietly(in); // 关掉缓冲输入流
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
