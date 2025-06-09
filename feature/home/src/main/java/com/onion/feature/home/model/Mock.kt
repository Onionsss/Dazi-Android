package com.onion.feature.home.model

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: Mock
 * Author: admin by 张琦
 * Date: 2024/11/28 14:37
 * Description:
 */
internal object Mock {

    fun homeBanner(): HomeBannerModel {
        return HomeBannerModel(
            arrayListOf(
                HomeBannerItem(
                    0,
                    "https://img1.baidu.com/it/u=4072711409,4195108353&fm=253&fmt=auto&app=138&f=JPEG?w=1427&h=800"
                ),
                HomeBannerItem(
                    1,
                    "https://i2.hdslb.com/bfs/archive/175a9827e4cc917e10091fb40eebfd2e89ea821a.jpg"
                ),
                HomeBannerItem(
                    2,
                    "https://pic.rmb.bdstatic.com/bjh/805bc8814da3ef94df368b2b1434a78f1772.jpeg@h_1280"
                ),
                HomeBannerItem(
                    3,
                    "https://pic.rmb.bdstatic.com/bjh/805bc8814da3ef94df368b2b1434a78f1772.jpeg@h_1280"
                ),
                HomeBannerItem(
                    4,
                    "https://img2.baidu.com/it/u=4063971693,1092708710&fm=253&fmt=auto&app=138&f=JPEG?w=1423&h=800"
                ),
            )
        )
    }

    fun homeKingKong(): HomeKingKongModel? {
        return HomeKingKongModel(
            list = arrayListOf(
                HomeKingKongItem(
                    "全站空头",
                    "https://img0.baidu.com/it/u=2653686457,2201625642&fm=253&fmt=auto&app=138&f=JPEG?w=665&h=665"
                ),
                HomeKingKongItem(
                    "数字头像",
                    "https://img2.baidu.com/it/u=2312744243,130959918&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=800"
                ),
                HomeKingKongItem(
                    "平台导航",
                    "https://b0.bdstatic.com/ugc/-kQVwWSKmAy-YnxC1AuLqA52c58f41fcb6cfef3b17d14fefdd6e39.jpg@h_1280"
                ),
                HomeKingKongItem(
                    "明文监控",
                    "https://img1.baidu.com/it/u=1141788953,2111016167&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=800"
                ),
                HomeKingKongItem(
                    "实时监控",
                    "https://ww4.sinaimg.cn/mw690/008xPD35ly1huif0ln97bj30gk0gkab2.jpg"
                ),
            )
        )
    }

    fun homeHot(): HomeHotModel? {
        return HomeHotModel(
            arrayListOf(
                HomeHotItem(
                    "https://img2.baidu.com/it/u=1110593205,1733492304&fm=253&fmt=auto&app=138&f=JPEG?w=647&h=500",
                    "长安汽车"
                ),
                HomeHotItem(
                    "https://img2.baidu.com/it/u=2926620382,3296136918&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
                    "中华科技"
                ),
                HomeHotItem(
                    "https://p4.itc.cn/images01/20230526/1722ec6ba57a4096b5177abb3c8c3146.png",
                    "保变电气"
                ),
                HomeHotItem("", "英赛尔特"),
                HomeHotItem("", "企划燃油"),
                HomeHotItem("", "数字头像"),
                HomeHotItem("", "我的全局"),
                HomeHotItem("", "实时监控"),
                HomeHotItem("", "上海煤气"),
                HomeHotItem("", "南通广电"),
            )
        )
    }

    fun homeCity(): HomeCityModel? {
        return HomeCityModel(
            "",
            "https://img2.baidu.com/it/u=2575555370,1390809248&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=800",
            "Today",
            "12月2日",
        )
    }

    fun homeList(): HomeListModel? {
        val list = arrayListOf<HomeListItem>()

        list.add(
            HomeListNew(
                "现已推出",
                "https://pic.rmb.bdstatic.com/bjh/231226/a84c1a3387a571831a5984b0ac2dfd2d3266.jpeg@h_1280",
                "重磅",
                "智勇兼全\n子龙一身都是胆也",
                "五虎将",
                publisher(),
                primaryColor = 0xFF24306C,
                primarySubColor = 0xFF243676
            )
        )

//        list.add(
//            HomeListNew(
//                "现已推出",
//                "https://pic.rmb.bdstatic.com/bjh/be5dea36d318c81a7e8b73d54ba119ac6886.jpeg@h_1280",
//                "重磅",
//                "智勇兼全\n子龙一身都是胆也",
//                "五虎将",
//                publisher(),
//                primaryColor = 0xFF7F120B,
//                primarySubColor = 0xFF90130D
//            )
//        )
//
//        list.add(
//            HomeListNew(
//                "现已推出",
//                "https://img0.baidu.com/it/u=1956576359,4003895590&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=1125",
//                "重磅",
//                "智勇兼全\n子龙一身都是胆也",
//                "五虎将",
//                publisher(),
//                primaryColor = 0xFF7F120B,
//                primarySubColor = 0xFF90130D
//            )
//        )

        list.add(
            HomeListTwoCard(
                arrayListOf(
                    HomeListTwoCardItem(
                        "https://img0.baidu.com/it/u=1956576359,4003895590&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=1125",
                        "现已推出",
                        "五虎将之奇旅",
                        0xFF34431B,
                        0xFF34431B,
                    ),
                    HomeListTwoCardItem(
                        "https://img0.baidu.com/it/u=1956576359,4003895590&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=1125",
                        "进行中",
                        "五虎将之奇旅",
                        0xFF453FB2,
                        0xFF453FB2,
                    )
                )
            )
        )

        list.add(HomePublisherScrollCard(
            "https://i1.hdslb.com/bfs/archive/dfbff02971dde4945597c3acfb8706b656289f19.jpg","主打推荐","不可错过的更新",
            arrayListOf(
                HomeScrollMix(publisher(), ),
                HomeScrollMix(publisherWang(),),
                HomeScrollMix(publisherByte(),),
                HomeScrollMix(publisherDou(),),
                HomeScrollMix(publisherDou(),),
                HomeScrollMix(publisherWang(),),
                HomeScrollMix(publisherDou(),),
                HomeScrollMix(publisherByte(),),
            )
        ))
        list.add(
            HomeListSingleCard(
                imageUrl = "https://i1.hdslb.com/bfs/archive/dfbff02971dde4945597c3acfb8706b656289f19.jpg",
                topTitle = "AppStore精品佳作",
                topSubTitle = "欢乐仍在延续",
                tag = "玩起来",
                title = "涂鸦少年跑遍世界",
                subTitle = "重新打开地铁跑酷,发现经典新亮点",
                0xFF453FB2,
                0xFF453FB2,
            )
        )

        list.add(
            HomeFourPublisherCard(
                "https://i1.hdslb.com/bfs/archive/dfbff02971dde4945597c3acfb8706b656289f19.jpg",
                arrayListOf(publisher(), publisher(), publisher(), publisher())
            )
        )

        list.add(HomeDayChooseCard(
            "https://img2.baidu.com/it/u=2970783500,4003644143&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800",
            "每日精选",
            "编辑最爱",
            "新用户?\n试试这些",
            arrayListOf(publisher(), publisher(), publisher())
        ))

        list.add(
            HomeListSinglePublisher(
                imageUrl = "https://i1.hdslb.com/bfs/archive/dfbff02971dde4945597c3acfb8706b656289f19.jpg",
                tag = "精品",
                title = "探索数字艺术的未来，拥抱不可替代的收藏品——我们不仅是 NFT 的创作者，更是革新者。每一件作品都是技术与创意的完美结合，带给你前所未有的数字拥有体验。",
                subTitle = "进入数字资产的新纪元，每个 NFT 都是你独一无二的数字资产，让你拥有从未有过的数字收藏体验。为创作者提供平台，为收藏者开辟新天地，我们是连接创意与价值的桥梁。",
                publisher = publisher()
            )
        )
        return HomeListModel(1, list)
    }

    fun publisher(): NftPublisher {
        return NftPublisher(
            "https://p2.itc.cn/images01/20220527/e107790014c24bc999eb635c9e45e8a3.jpeg",
            "Tacoo",
            "全新的国外发行商"
        )
    }

    fun publisherWang(): NftPublisher {
        return NftPublisher(
            "https://img2.baidu.com/it/u=2485255804,834090827&fm=253&fmt=auto?w=898&h=800",
            "网易",
            "全新的国外发行商"
        )
    }

    fun publisherDou(): NftPublisher {
        return NftPublisher(
            "https://gd-hbimg.huaban.com/29b5de1070bdee5d1a56b38844e3ca895e7914a97be0-fZiNXq_fw658",
            "抖音",
            "全新的国外发行商"
        )
    }

    fun publisherByte(): NftPublisher {
        return NftPublisher(
            "https://b0.bdstatic.com/c6823e92fe654329dac2c46a0bfea17c.jpg@h_1280",
            "字节跳动",
            "全新的国外发行商"
        )
    }
}