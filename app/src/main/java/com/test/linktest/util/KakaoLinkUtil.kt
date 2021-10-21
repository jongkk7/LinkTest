package com.test.linktest.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.link.WebSharerClient
import com.kakao.sdk.template.model.*

/**
 * Kakao Link
 *
 * Android 플랫폼 > 키해시 추가
 * https://developers.kakao.com/docs/latest/ko/message/message-template#link
 */
class KakaoLinkUtil {

    private val TAG = "###${javaClass.simpleName}"

    /**
     * kakao developer
     * 내 애플리케이션 > 앱 설정 > 플랫폼 > Web 사이트도메인 추가 ( 필수 )
     */
    private val link = "https://www.naver.com";

    /**
     * test data
     */
    private val title = "농협 기업뱅킹"
    private val message = "100,000원이 이체되었습니다."
    private val imageUrl = "https://shop-phinf.pstatic.net/20210130_73/1611933594859cHb69_PNG/13069374461629718_2111905295.png?type=o640"
    private val address = "계산로 51-1";

    companion object {

        val APP_KEY = "6dd42dc0bed9e59fecc0a3c1f07f33b0"

        fun init(context: Context) {
            KakaoSdk.init(context, APP_KEY)
        }

    }

    fun sendMessage(context: Context, message: String) {

        // 카카오톡 설치여부 확인
        if (LinkClient.instance.isKakaoLinkAvailable(context)) {
            // 카카오톡으로 카카오링크 공유 가능
            LinkClient.instance.defaultTemplate(context, getTextMessage(message)) { linkResult, error ->
                if (error != null) {
                    Log.e(TAG, "카카오링크 보내기 실패", error)
                } else if (linkResult != null) {
                    Log.d(TAG, "카카오링크 보내기 성공 ${linkResult.intent}")
                    context.startActivity(linkResult.intent)

                    // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                    Log.w(TAG, "Warning Msg: ${linkResult.warningMsg}")
                    Log.w(TAG, "Argument Msg: ${linkResult.argumentMsg}")
                }
            }
        } else {
            // 카카오톡 미설치: 웹 공유 사용 권장
            // 웹 공유 예시 코드
            val sharerUrl = WebSharerClient.instance.defaultTemplateUri(getFeedMessage(title, message))

            // CustomTabs으로 웹 브라우저 열기

            // 1. CustomTabs으로 Chrome 브라우저 열기
            try {
                KakaoCustomTabsClient.openWithDefault(context, sharerUrl)
            } catch (e: UnsupportedOperationException) {
                // Chrome 브라우저가 없을 때 예외처리
            }

            // 2. CustomTabs으로 디바이스 기본 브라우저 열기
            try {
                KakaoCustomTabsClient.open(context, sharerUrl)
            } catch (e: ActivityNotFoundException) {
                // 인터넷 브라우저가 없을 때 예외처리
            }
        }

    }

    fun test(context: Context){

        if (LinkClient.instance.isKakaoLinkAvailable(context)) {

            // 텍스트
            val text = getTextMessage(message)

            // 피드
            val feed = getFeedMessage(title, message)

            // 위치
            val location =  getLocationMessage(address , title , message, imageUrl)


            // 카카오톡으로 카카오링크 공유 가능
            val obj = feed
            LinkClient.instance.defaultTemplate(context, obj) { linkResult, error ->
                if (error != null) {
                    Log.e(TAG, "카카오링크 보내기 실패", error)
                } else if (linkResult != null) {
                    Log.d(TAG, "카카오링크 보내기 성공 ${linkResult.intent}")
                    context.startActivity(linkResult.intent)

                    // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                    Log.w(TAG, "Warning Msg: ${linkResult.warningMsg}")
                    Log.w(TAG, "Argument Msg: ${linkResult.argumentMsg}")
                }
            }
        }
    }

    /**
     * Text 형식
     */
    fun getTextMessage(message: String): TextTemplate {
        val textFeed = TextTemplate(
                message,
                link = getCommonLink(link,link),
                buttons = listOf(
                        Button(
                                "기업뱅킹 열기",
                                getCommonLink(link,link)
                        )
                )
        )

        return textFeed
    }

    /**
     * Feed 형식
     * - Image ( 기본 1장, 최대 3장 )
     * - Title/Description ( 각각 최대 2줄 )
     * - Social ( 최대 3개 표시 (순서: 좋아요 > 댓글 > 공유 > 조회 > 구독 )
     * - Button ( 최대 2개 )
     */
    fun getFeedMessage(title : String, message : String): FeedTemplate {
        val defaultFeed = FeedTemplate(
                content = getCommonContents(title, message, imageUrl, link),
                social = getCommonSocial(1875, 268, 92),
                buttons = listOf(
                        Button(
                                "웹으로 보기",
                                getCommonLink(link,link)
                        ),
                        Button(
                                "앱으로 보기",
                                getCommonLink(link,link)
                        )
                )
        )

        return defaultFeed
    }

    /**
     * Location 형식
     * - Image ( 최대 1장 )
     * - Title/Description ( 최대 4줄 )
     * - Social ( 최대 3개 표시 (순서: 좋아요 > 댓글 > 공유 > 조회 > 구독 )
     * - Button ( 최대 2개 )
     */
    fun getLocationMessage(address : String, title: String, messages: String, imageUrl: String ) : LocationTemplate{
        return LocationTemplate(
                address,
                getCommonContents(title, messages, imageUrl, link),
                buttons = listOf(
                        Button(
                                "웹으로 보기",
                                getCommonLink(link,link)
                        )
                )
        )
    }

    /**
     * Common
     */
    fun getCommonContents(title: String, message: String, imageUrl: String, link: String): Content {
        return Content(
                title = title,
                description = message,
                imageUrl = imageUrl,
                link = Link(
                        webUrl = link,
                        mobileWebUrl = link
                )
        )
    }

    fun getCommonSocial(like: Int, comment: Int, shared: Int): Social {
        return Social(
                likeCount = like,
                commentCount = comment,
                sharedCount = shared
        )
    }

    fun getCommonLink(webLink : String, mobileLink : String): Link{
        return Link(
                mobileWebUrl = mobileLink
        )
    }
}