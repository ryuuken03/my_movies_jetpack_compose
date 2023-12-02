package mohammad.toriq.mymovies.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

/***
 * Created By Mohammad Toriq on 01/12/2023
 */
class Util {
    companion object{
        fun convertDate(
            dateInput: String?,
            inFormat: String?,
            outFormat: String?,
            isLocal: Boolean = false
        ): String {
            var tmp = ""

            val locale = Locale("in")
            val origin =
                SimpleDateFormat(inFormat, Locale.getDefault())
            origin.timeZone = TimeZone.getTimeZone("UTC")
            if (isLocal) {
                origin.timeZone = TimeZone.getDefault()
            }
            val result = SimpleDateFormat(outFormat, locale)
            result.timeZone = TimeZone.getDefault()

            try {
                tmp = result.format(origin.parse(dateInput))
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return tmp
        }
        fun getShowTime(time: Int /** in minute**/): String {
            var showTime = ""
            var hour = time/ 60
            var minute = time % 60

            var hourText = hour.toString()

            var minuteText = minute.toString()

            showTime = hourText+" Hour "+minuteText+" Minute"
            return showTime
        }
    }
}