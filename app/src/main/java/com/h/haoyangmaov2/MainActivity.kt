package com.h.haoyangmaov2

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSON
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.h.haoyangmaov2.databinding.ActivityMainBinding
import com.hjq.toast.ToastUtils
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainActivity : BaseAc<ActivityMainBinding>(), SocketMessageListener {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ListAdapter
    var list = ArrayList<ConnectWalletBean>()
    val zzstring =
        "{\"0xb3D23147499AE89dB2E8D22e5E9026680568aAb6\":{\"exp\":5527,\"grade_id\":1,\"id\":122,\"invite_code\":\"561858\",\"level\":16,\"mem_address\":\"0xb3D23147499AE89dB2E8D22e5E9026680568aAb6\",\"need_exp\":\"680\",\"now_exp\":\"267\",\"token\":\"1338241bdde03947d045f92ff4d35a62\",\"walletAddress\":\"0xb3D23147499AE89dB2E8D22e5E9026680568aAb6\"},\"0x7B93Fcd883242555E37694CDbD97A14a55Ee60cD\":{\"exp\":5281,\"grade_id\":1,\"id\":7238,\"invite_code\":\"451966\",\"level\":16,\"mem_address\":\"0x7B93Fcd883242555E37694CDbD97A14a55Ee60cD\",\"need_exp\":\"680\",\"now_exp\":\"21\",\"token\":\"99a00172d3c42cde997227f9a46b254a\",\"walletAddress\":\"0x7B93Fcd883242555E37694CDbD97A14a55Ee60cD\"},\"0x324697157f5BBbD4C1dDC0cdAFf3C5593480002D\":{\"exp\":2320,\"grade_id\":1,\"id\":2147,\"invite_code\":\"429196\",\"level\":11,\"mem_address\":\"0x324697157f5BBbD4C1dDC0cdAFf3C5593480002D\",\"need_exp\":\"550\",\"now_exp\":\"60\",\"token\":\"c983c4b1afe7b744fa2e9854d6b341fe\",\"walletAddress\":\"0x324697157f5BBbD4C1dDC0cdAFf3C5593480002D\"},\"0x37bC594257F6f7CAa785Cc732771a2f7642606c3\":{\"exp\":1773,\"grade_id\":1,\"id\":32625,\"level\":10,\"mem_address\":\"0x37bC594257F6f7CAa785Cc732771a2f7642606c3\",\"need_exp\":\"520\",\"now_exp\":\"33\",\"token\":\"2392982bcadd271dfceb692ebdd6939b\",\"walletAddress\":\"0x37bC594257F6f7CAa785Cc732771a2f7642606c3\"},\"0x83BC081D4148f5fB09972049428561B07a1775E3\":{\"exp\":1544,\"grade_id\":1,\"id\":33348,\"level\":9,\"mem_address\":\"0x83BC081D4148f5fB09972049428561B07a1775E3\",\"need_exp\":\"500\",\"now_exp\":\"304\",\"token\":\"fdc27d6a74c624b40d4462fe8f45fe9f\",\"walletAddress\":\"0x83BC081D4148f5fB09972049428561B07a1775E3\"},\"0x8450042f123fDDeb5bB69991C422d756Cb2Ee424\":{\"exp\":1670,\"grade_id\":1,\"id\":32765,\"level\":9,\"mem_address\":\"0x8450042f123fDDeb5bB69991C422d756Cb2Ee424\",\"need_exp\":\"500\",\"now_exp\":\"430\",\"token\":\"96412b0b4920ab742c43f47704d4d944\",\"walletAddress\":\"0x8450042f123fDDeb5bB69991C422d756Cb2Ee424\"},\"0x127CDB2A725A3B19169bd272EFc11A05F8a9c1D6\":{\"exp\":1608,\"grade_id\":1,\"id\":32761,\"level\":9,\"mem_address\":\"0x127CDB2A725A3B19169bd272EFc11A05F8a9c1D6\",\"need_exp\":\"500\",\"now_exp\":\"368\",\"token\":\"b438f78584ac5b829b5d9a6456c6baff\",\"walletAddress\":\"0x127CDB2A725A3B19169bd272EFc11A05F8a9c1D6\"},\"0x03877A88b5bbC5b00fdE7e16a4aec8086B4dD0c7\":{\"exp\":1360,\"grade_id\":1,\"id\":35005,\"level\":9,\"mem_address\":\"0x03877A88b5bbC5b00fdE7e16a4aec8086B4dD0c7\",\"need_exp\":\"500\",\"now_exp\":\"120\",\"token\":\"c9a686b7f6e250137138657d8f2312aa\",\"walletAddress\":\"0x03877A88b5bbC5b00fdE7e16a4aec8086B4dD0c7\"},\"0xf04559D4AF87f4ba4659614B6e39BAD1B10356BB\":{\"exp\":1669,\"grade_id\":1,\"id\":32762,\"level\":9,\"mem_address\":\"0xf04559D4AF87f4ba4659614B6e39BAD1B10356BB\",\"need_exp\":\"500\",\"now_exp\":\"429\",\"token\":\"f97d947e8f6a75cc315cb7201548e130\",\"walletAddress\":\"0xf04559D4AF87f4ba4659614B6e39BAD1B10356BB\"},\"0x0F9CE2D5A6Bc1043A66874e1162A370E0D173FE5\":{\"exp\":1552,\"grade_id\":1,\"id\":33347,\"level\":9,\"mem_address\":\"0x0F9CE2D5A6Bc1043A66874e1162A370E0D173FE5\",\"need_exp\":\"500\",\"now_exp\":\"312\",\"token\":\"7b85c91ac192801b6c6d3ab0d46e9622\",\"walletAddress\":\"0x0F9CE2D5A6Bc1043A66874e1162A370E0D173FE5\"},\"0x7Fd2415DBfD7F728d3E7A204d9a002ea5B83D9d8\":{\"exp\":1542,\"grade_id\":1,\"id\":33351,\"level\":9,\"mem_address\":\"0x7Fd2415DBfD7F728d3E7A204d9a002ea5B83D9d8\",\"need_exp\":\"500\",\"now_exp\":\"302\",\"token\":\"77b81eb166f73aa486dc881d835ea453\",\"walletAddress\":\"0x7Fd2415DBfD7F728d3E7A204d9a002ea5B83D9d8\"},\"0xa79C48eFA74059c0BE4cF7278571887C6845D0Db\":{\"exp\":1731,\"grade_id\":1,\"id\":32786,\"level\":9,\"mem_address\":\"0xa79C48eFA74059c0BE4cF7278571887C6845D0Db\",\"need_exp\":\"500\",\"now_exp\":\"431\",\"token\":\"acf519c1f478fca8f617948146532c64\",\"walletAddress\":\"0xa79C48eFA74059c0BE4cF7278571887C6845D0Db\"},\"0x1A7D935E99AE9b7Ad0f2Adc94163C47194D4eDA7\":{\"exp\":1362,\"grade_id\":1,\"id\":35004,\"level\":9,\"mem_address\":\"0x1A7D935E99AE9b7Ad0f2Adc94163C47194D4eDA7\",\"need_exp\":\"500\",\"now_exp\":\"122\",\"token\":\"62906a0f5697fad11c84ff55b9fe633e\",\"walletAddress\":\"0x1A7D935E99AE9b7Ad0f2Adc94163C47194D4eDA7\"},\"0xB0bde61f3a033a059d34A770707cf150F3576072\":{\"exp\":1542,\"grade_id\":1,\"id\":33379,\"level\":9,\"mem_address\":\"0xB0bde61f3a033a059d34A770707cf150F3576072\",\"need_exp\":\"500\",\"now_exp\":\"302\",\"token\":\"fb3de193d45848b17f047e394ef635f4\",\"walletAddress\":\"0xB0bde61f3a033a059d34A770707cf150F3576072\"},\"0x5B090680a7b8d689787b53a652C7813dfA74c509\":{\"exp\":1543,\"grade_id\":1,\"id\":33378,\"level\":9,\"mem_address\":\"0x5B090680a7b8d689787b53a652C7813dfA74c509\",\"need_exp\":\"500\",\"now_exp\":\"303\",\"token\":\"a1b386ad832d461fa6be4f260f8dbbbb\",\"walletAddress\":\"0x5B090680a7b8d689787b53a652C7813dfA74c509\"},\"0xd282B7C043e8033ceaA6b5F2628309Df1d9A0170\":{\"exp\":1673,\"grade_id\":1,\"id\":32785,\"level\":9,\"mem_address\":\"0xd282B7C043e8033ceaA6b5F2628309Df1d9A0170\",\"need_exp\":\"500\",\"now_exp\":\"433\",\"token\":\"e640027032aa16cb52b216834b631dd7\",\"walletAddress\":\"0xd282B7C043e8033ceaA6b5F2628309Df1d9A0170\"},\"0x846EA403671E66F1529c78FFa0EBa40fC2253661\":{\"exp\":1734,\"grade_id\":1,\"id\":32621,\"level\":9,\"mem_address\":\"0x846EA403671E66F1529c78FFa0EBa40fC2253661\",\"need_exp\":\"500\",\"now_exp\":\"494\",\"token\":\"a1924814a2590d2b0efb9f92ac009d98\",\"walletAddress\":\"0x846EA403671E66F1529c78FFa0EBa40fC2253661\"},\"0x3Fb93aa58d1C165367a860B4A8329597eDd68C1f\":{\"exp\":1544,\"grade_id\":1,\"id\":33350,\"level\":9,\"mem_address\":\"0x3Fb93aa58d1C165367a860B4A8329597eDd68C1f\",\"need_exp\":\"500\",\"now_exp\":\"304\",\"token\":\"7584b923214c366401db9c84a654abaa\",\"walletAddress\":\"0x3Fb93aa58d1C165367a860B4A8329597eDd68C1f\"},\"0x8fdCa05bcBBd8384a2d36DB2b94F6A69779e5034\":{\"exp\":1480,\"grade_id\":1,\"id\":35003,\"level\":9,\"mem_address\":\"0x8fdCa05bcBBd8384a2d36DB2b94F6A69779e5034\",\"need_exp\":\"500\",\"now_exp\":\"240\",\"token\":\"7e775562bf947a18639eeadfc1f6bd9d\",\"walletAddress\":\"0x8fdCa05bcBBd8384a2d36DB2b94F6A69779e5034\"},\"0x5088Eb13d2f8991313200BE81616796cBAF6093b\":{\"exp\":1361,\"grade_id\":1,\"id\":35007,\"level\":9,\"mem_address\":\"0x5088Eb13d2f8991313200BE81616796cBAF6093b\",\"need_exp\":\"500\",\"now_exp\":\"121\",\"token\":\"242073a89ea920d9a843d7dcae978044\",\"walletAddress\":\"0x5088Eb13d2f8991313200BE81616796cBAF6093b\"},\"0xbb9CC27c1F417b31C91A8eDB32e0C3D82CE4E1BC\":{\"exp\":1732,\"grade_id\":1,\"id\":32623,\"level\":9,\"mem_address\":\"0xbb9CC27c1F417b31C91A8eDB32e0C3D82CE4E1BC\",\"need_exp\":\"500\",\"now_exp\":\"492\",\"token\":\"54e179c30fe155726677440cdcb5089b\",\"walletAddress\":\"0xbb9CC27c1F417b31C91A8eDB32e0C3D82CE4E1BC\"},\"0x001De1677f528108841BcDc48BE3DA0de8240EcB\":{\"exp\":1668,\"grade_id\":1,\"id\":32763,\"level\":9,\"mem_address\":\"0x001De1677f528108841BcDc48BE3DA0de8240EcB\",\"need_exp\":\"500\",\"now_exp\":\"428\",\"token\":\"61e760883cded01e0fd4583f070ac76b\",\"walletAddress\":\"0x001De1677f528108841BcDc48BE3DA0de8240EcB\"},\"0x9C89D52EaD5dA08661d8BbaCf987e56300B60baE\":{\"exp\":1735,\"grade_id\":1,\"id\":32629,\"level\":9,\"mem_address\":\"0x9C89D52EaD5dA08661d8BbaCf987e56300B60baE\",\"need_exp\":\"500\",\"now_exp\":\"495\",\"token\":\"30af5cb18b1ca36e904fb9449c1c9874\",\"walletAddress\":\"0x9C89D52EaD5dA08661d8BbaCf987e56300B60baE\"},\"0xdAaF53436635003805461529A1b4B30485F53525\":{\"exp\":1546,\"grade_id\":1,\"id\":33345,\"level\":9,\"mem_address\":\"0xdAaF53436635003805461529A1b4B30485F53525\",\"need_exp\":\"500\",\"now_exp\":\"306\",\"token\":\"e89c5710db87013bb659884383d25034\",\"walletAddress\":\"0xdAaF53436635003805461529A1b4B30485F53525\"},\"0x8465c5dbb638e536874307Cc00160849B7a54Dec\":{\"exp\":1672,\"grade_id\":1,\"id\":32784,\"level\":9,\"mem_address\":\"0x8465c5dbb638e536874307Cc00160849B7a54Dec\",\"need_exp\":\"500\",\"now_exp\":\"432\",\"token\":\"1cd1c00a4de684ae723a6aece25cedec\",\"walletAddress\":\"0x8465c5dbb638e536874307Cc00160849B7a54Dec\"},\"0x7772e7f93E1DDcE68E68a2ef45d7632B00bc1D60\":{\"exp\":162,\"grade_id\":1,\"id\":38564,\"invite_code\":\"\",\"level\":3,\"mem_address\":\"0x7772e7f93E1DDcE68E68a2ef45d7632B00bc1D60\",\"need_exp\":\"90\",\"now_exp\":\"82\",\"token\":\"d256da142f08d51db394faba592471e2\",\"walletAddress\":\"0x7772e7f93E1DDcE68E68a2ef45d7632B00bc1D60\"},\"0x7bb9a6efe7eB156bd31792537B91ecb3E8cAb538\":{\"exp\":1543,\"grade_id\":1,\"id\":33352,\"level\":9,\"mem_address\":\"0x7bb9a6efe7eB156bd31792537B91ecb3E8cAb538\",\"need_exp\":\"500\",\"now_exp\":\"243\",\"token\":\"e7019d330cba0cfd7844433ea6fd8b6e\",\"walletAddress\":\"0x7bb9a6efe7eB156bd31792537B91ecb3E8cAb538\"}}"

    override fun initViews() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initRecycler()
        setViewModelData()
        loadWalletInfo()
        WebSocketUtils.instance.setMessageListener(this)
        adapter.setOnItemClickListener { adapter, view, position ->

            copyToClip2(view.context, (adapter.data[position] as ConnectWalletBean).walletAddress)
        }
    }

    /**
     * 初始化列表
     */
    private fun initRecycler() {
        adapter = ListAdapter(R.layout.item_list)
        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter = adapter
    }


    /**
     * 读取信息
     */
    private fun loadWalletInfo() {
        val decodeMap = SpUtils.decodeString("map")
//        Logs.e("decodeMap=$decodeMap")
        if (!decodeMap.equals("") and decodeMap.isNotBlank() and decodeMap.isNotEmpty()) {
            map = Gson().fromJson(
                decodeMap,
                object : TypeToken<HashMap<String, ConnectWalletBean>>() {}.type
            )
            ToastUtils.show("读取成功")
            setAdapterList()
        }
    }

    /**
     * 按照等级排序
     */
    private fun sortMap(): HashMap<String, ConnectWalletBean> {
        map = map.entries.sortedBy {
            it.value.level
        }.reversed().associateBy({ it.key },
            { it.value }) as HashMap<String, ConnectWalletBean>
//        Logs.e("SortMap=$map")
        return map
    }

    /**
     * 设置adapter数据
     */
    private fun setAdapterList() {
        list.clear()
        sortMap()
        map.forEach {
            it.key
            it.value
            list.add(it.value)
        }
//        Logs.e("list=${list.size}")
        adapter.setList(list)
    }

    private fun saveWalletInfo(map: HashMap<String, ConnectWalletBean>) {
        val mapJson = JSON.toJSONString(map)
        SpUtils.encode("map", mapJson)
        setAdapterList()
    }

    var map = HashMap<String, ConnectWalletBean>()

    /**
     * viewmodel回传数据
     */
    private fun setViewModelData() {
        viewModel.addressLiveData.observe(this) {
            ToastUtils.show("加载完成")
            map[it.walletAddress] = it
            saveWalletInfo(map)
        }
        viewModel.getBoxStatusLiveData.observe(this) {
            map.forEach { map1 ->
                map1.key
                map1.value
                viewModel.getLevelBox(it.id.toString(), map1.key)
            }
        }
        viewModel.getAirDropWithDrawLiveData.observe(this) {
            if (it.receiveStatus == 1) {
                map[it.walletAddress]?.giftStatus = "1"
                adapter.notifyDataSetChanged()
            }
        }
    }

    var timer: Timer? = null

    /**
     * 开始挂机
     */
    fun startLevelUp(view: View) {
        WebSocketUtils.instance.startRequest(this)
        startTimer()
    }

    private fun startTimer() {
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    saveWalletInfo(map)
                }

            }
        }, 0, 1000 * 61)
    }

    /**
     * 保存地址
     */
    fun saveAddress(view: View) {
        if (binding.etInput.text.isBlank()) {
            return
        }
        ToastUtils.show("加载中")
        viewModel.inputAddress(binding.etInput.text.toString())
        binding.etInput.text.clear()
    }

    override fun setMessage(message: String?) {
        if (message.isNullOrEmpty()) {
            return
        }
        val obj = JSONObject(message)
        //增加经验
        if (obj.has("controller") && obj.getString("controller") == "Exp" && obj.has("exp_info")) {
            val expBean: SocketResponseExpBean = Gson().fromJson(
                message,
                object : TypeToken<SocketResponseExpBean>() {}.type
            )
            if (map.containsKey(expBean.address)) {
                map[expBean.address]?.now_exp = expBean.exp_info.now_level_exp
                map[expBean.address]?.need_exp = expBean.exp_info.need_exp
                map[expBean.address]?.level = expBean.exp_info.member_level


            }

            return
        }
    }

    fun copy(view: View) {
        copyToClip(this)
    }

    fun paste(view: View) {
        map = Gson().fromJson(
            zzstring,
            object : TypeToken<HashMap<String, ConnectWalletBean>>() {}.type
        )
        setAdapterList()
//        Logs.e("map=$map")
    }

    fun copyToClip2(context: Context, str: String) {
        if (SpUtils.decodeString("map").equals("")) {
            return
        }
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        //clipData中的this就是需要复制的文本
        val clipData = ClipData.newPlainText("", str)
        cm.setPrimaryClip(clipData)
        Logs.e("clipData=$str")
    }

    fun copyToClip(context: Context) {
        if (SpUtils.decodeString("map").equals("")) {
            return
        }
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        //clipData中的this就是需要复制的文本
        val clipData = ClipData.newPlainText("", SpUtils.decodeString("map"))
        cm.setPrimaryClip(clipData)
        Logs.e("clipData=$clipData")
    }

    fun activityId(view: View) {
        viewModel.getBoxStatus(adapter.data[0].walletAddress)
    }

}