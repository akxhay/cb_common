package com.cb.cbpreference.presentation.composable.preference

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cb.cb_permission.constants.PreferenceType
import com.cb.cbpreference.data.Preference
import com.cb.cbpreference.data.PreferenceIcon
import com.cb.cbpreference.util.IconResolver


@Composable
fun PreferenceCategory(modifier: Modifier, title: String, color: Color) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(70.dp))
            PreferenceCategoryTitle(title, color)
        }
    }

}

@Composable
fun PreferenceHeader(modifier: Modifier, title: String, color: Color) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(70.dp))
            PreferenceCategoryTitle(title, color)
        }
    }

}

@Composable
fun PreferenceComposable(
    preference: Preference,
    modifier: Modifier,
    icon: ImageVector,
    titleColor: Color,
    summaryColor: Color,
    iconColor: Color,
    preferenceType: PreferenceType = PreferenceType.DEFAULT,
    onclick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp)
            .clickable {
                onclick()
            },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            if (preference.icon != null) {
                PreferenceIcon(preference.icon!!)
            } else {
                PreferenceIcon(icon, iconColor)
            }

            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(3.0f, true)) {
                PreferenceTitle(preference.title!!, titleColor)
                preference.summary?.let {
                    Spacer(modifier = Modifier.height(2.dp))
                    PreferenceSummary(it, summaryColor)
                }
            }
            if (preferenceType != PreferenceType.DEFAULT) {
                Column(
                    modifier = Modifier.weight(1.0f, true),
                    horizontalAlignment = Alignment.End
                ) {
                    PreferenceAction(
                        preferenceType
                    )

                }
            }
        }
    }

}

@Composable
fun PreferenceAction(preferenceType: PreferenceType) {
    Box(
        modifier = Modifier
            .size(30.dp),
        contentAlignment = Alignment.Center
    ) {
        if (preferenceType == PreferenceType.SWITCH)
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                tint = Color.Green
            )
    }

}

@Composable
fun PreferenceCategoryTitle(title: String, color: Color) {
    Text(
        text = title,
        style = TextStyle(
            fontSize = 14.sp,
            color = color,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}


@Composable
fun PreferenceTitle(title: String, titleColor: Color) {
    Text(
        text = title,
        style = TextStyle(
            fontSize = 18.sp,
            color = titleColor,
            fontWeight = FontWeight.Medium
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}


@Composable
fun PreferenceSummary(summary: String, summaryColor: Color) {
    Text(
        text = summary,
        style = TextStyle(
            fontSize = 15.sp,
            color = summaryColor,
            fontWeight = FontWeight.Light
        ),
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun PreferenceIcon(icon: ImageVector, iconColor: Color) {
    val value =
        "iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAABmJLR0QA/wD/AP+gvaeTAAAWtUlEQVR4nLWbeXwVVZbHv/dW1ctbkpdAAmQlJCwBZFFBlAZUVHBDkbbVcbp7BJ2eXkRbsXXmMzPOh8+nZ6Y/3eLWKmO7MUpPazNtAw4CLS4MKqh0+0EkYBZC9o3sy3vJe1V15496eS8ve4D5/ZGlqu6953fuueece+qW4AJhywOvzRWwAiUuQ4gClMoDJgKeyCNhgWhQqAqBKrSRf7Gl+uixX28ouVAynAvE+TR+5qevFliWuBfEHUDeOXZzGvidsK03Nm3929LzkedccE4K2PKTV5dLKf5RIW7o6yMhATIzLDIyFBNSbPzJCo8bdEMBYIYFgSB0dghaWgX19ZLaOklvb1QEG3hHYP/bpufv+/MF4DYmjEsBTz+0bZptsgW4HUDTYMZ0i1mzLLIybcQ41akUVNdoFBdLTpdpWJZzGXjTNrWHH33xbxrH1+P4MWaRn3xg2w+weRJBkqYp5s+zWbjAxOtVozdWo48UCAiOfaVxolDDsgRCqAYssX7T1g37xyrjuWBUBTz18A6PCnW/jOC7ALm5NsuXhfEnxRNXCurqJQ0NkoYGQWurpCck6O1x7nk8zpJITbVZuTKMJocer6ND8MmnOhWVGoBCqec6JyU+unnznaHzZjsERlTAL378XxNcWmgvcIWmKVYsM5kzxxok8DdFGt8UaXR3j25QUgj1ve9bZ3w+K+/o586imT/fwu2OV+jJUxqffKpjWQIEHxgY337wue91jJfgaBhW4ufvfz01JO0PlWKB16NYc1OY1DQ7er+3R/DFn3VOntSwI7K7PYLcfIOcaTr+FEGSX+JNlGgSggGb7k6FkZDApOwpWKbNC/9ehBlW6DrMnWty+RILXYsp4myTZO8+g0BAIODzsNde9fe/uq/z/10Bv/m733g7Xcb7ArHU71fccnMIvz8mWFGRxqdH9KgHn5qns2hpAplZOmIY0wYQmgvDkwY4DzXWBTj8fg1lJSFQcM3KMAWz4i2svUOw510XHR0CAR/6QqEbfvjSD8PnyTsKfaiLnS7XVgFLPR7FzTfFyFs2HD6ic+KE0ywzW+dbK92kZ2qjDiS1BHRPGn06V7bNBH8HN63z0NzkoqpCp2BhAnRXoexYREmOTMDO3S4CAXFNV0LCs8BPzp+6g0EW8OQDr/0QJV7UNMXtt8XM3rQE+/YZVNdIhIAly9wsXpow4oxHBxkw80rZmMGzKNuZSM3lR3P5Aehu7+L1FypIn2Kx6towemSKmpoEO3cnYJoglLhr0wvrd1wA/sSJ/+xP35iKLZ4AWLHMjJK3bXjvgEM+wSXUbXf7uGzZ2MhLLQHDMylG3rYxA41DkgcwPF40qanyco39B1zYEbeTlqZYtjRi+VL95pmfvjzlvJj3ydf/H8uynkaQlJtrx3n7Q4cMKiokhi7Umjt8IitnyJUzCEJz8Yftnfz+lQogMvM9Z1HKBAaTB9BFD7fd7RVer1BVlZJPj8TGmjvXYmqOhVKk2Jbxi3OjHI+oArb85NXlCr6taYrly2I+puyM5FSRhpRC3XS7V2Rkj77eITbztVVBaiq6R515ANvsxuxtYUKqZM0diULXBSdO6JSejs3T8uVm37JY/9TGbfPPnXpEzr4/hCb/CWD+PCua5PT0CA59bACweGmCyJk29pnXPan0dzGjzbxtBjB7Wp32QpKVn8GK69MB+PgTg56g81yyX3HRHAuFEgr+YdyMB0AD2LJx22wBT2saYvV1YQyHM0c+16mt1UidpLHqFi9yDImz0Fz0hJJ5b1c9B3bWYlmOMo99EaShzmJKtp/ElAlDktd8BWC2onsmIaRBepabitJu2lpMTFOQO9VxCBMnKgoLdWzF3BuWrEtZfdm6M+8d3dV8LgqQkR/3AGLGdCua2weD8M0px9xX3uAZNnUdivz2F85QUthBKBRLnEIhRVlJmB3bGulsjy2xKHnvTDTvdPTEAoR0ZkAIwXW3ZiIknPpGo6vbaePzKfLzLABNoR5G2Ke2bNz25q8e3DZzvArQAFZfdtsrCFK+tdSMxvy/fKlTW6c5Sc4VCWMib3jSeG9XPY21PeTPSmTd3YksW+liyXI38xZPpK0Fmhp66eoIM2tecoR8C5qvAM03w+lHTwEVBtXjkE3UaajpoeVsCEOHrCxHqYYBxSUaQgh0TaAU86XiBzdcvlYsvemSwwcPHrSHFbYf5DM/fbUAwbSEBMjKjLRRUFLirPf5l46dPEgqSroAuGq1gddn8fZvu/njmz2kpE3kurWZAJSXdEXJA9iBElTPGWfg3tNgtcb1v2DJRACKijVUJCHNzrZxuRRKKb5z7zRRMD8ZBG6lxM+TmnI/ePqh7RljUoBtyZUAmZlWNPtqbpZ0dUOCG3LzR3Z8A5OcPrP3+ZzfdTUmtZW9ACQlG2Tl+pg0xRUlL4R01rzqhFA12IFBY+TNSCTJb9DVJWhqlpF2kBmZsI62MDffmc1d9+WRlGQo4ErbND/bsnHb7FEVoOBSgIz0WK5fXuUMkjPNQA5a+wLpShuSvFLxVjfQ0wPcsX4y6+5OiCcfWfMDZz46ooTsaV4AqqtjAvXJXFflKC0r18tf/yRfZOR4AaYK+N8n73991gj8kQo1D2BCSkz45mbHFDKyBsd86c5G81+M0L1Dprf9yQ8d6gbMfB/5UZDukOJsUywU9cnc1hwrFfgSde7YMI38giSAyQj7vSd+9Mbk4fqVEpkD4E+OWUBHuzNIalp/BQikOxs9cTZCuDCSF4H0xpFXdhiXy2kb6PHQH+dDHmBKpssh2xZTQHKKI3Nba3ytRDcEa+7KJj3bA5ArDet3O+7YMWQGJxVqMoCnn6/r6HRm1eWJDSZdqU6cFhGhZRLo6ShE3MYmJ88NwPu7aunsiIW78yGvbJMErQ0grujidkVCdrc1qI1uSNZ+dypJfkOhuLZySteDQyoAcAHorpgFWKbzt6HFBrNDTZjth8GO1CNClajeMsxAfVx6e+UNWbg9kjMlXbz8RHG0/UtPd7B3Z4DOQPK4yZvBs0jNMfdwOCaT4YqXdyB8iTqrbssUAALx818+8HL2UAoYBBkxFq2fnEJzYbgnQKgWrA6U2TLklnZCWgLfv38Gs+b5cSXEug+FFGXFYd56uTIuERoLeaUsNN0hPlTl2baHL8xOm5nIzIv8AD5DaY8PvK8DQcBjhgRapC4nIzPfG1SQPMDbq15UqGLY/Tw44e6m21Mxe/qElgTDKXzwzlnOlHTxv/vrWXNXzpjJO7I4FuDxxMiGI0vf5R55g3b1jemUnepUls36px/avvnhZ75f13dPAi0Awd5Yg8RIDG9rsxFaAoYnjcIv29mzo4pQyBxk9iN5+75EyJ/ijUuExkMeoK3VIe52x57rCYmIUkZWQFKywfSLkgTgUmHrB/3vSYQ4A9DeHjPXiROcwdrbBIYnFZAUF3ZS/HUHRcdqx0xeCEldjUlNRTAqCECod/gsdSjyAB1tzv8p/aJVeyQiJCbHJ2vBbnPQGAsWORswJey/ilcAfAPx4WXiRGeQhtq+RyAn3wl5xYXBMZPXPZOGJToUlD4JM9g0iDxAbbVzLSM9dq+1zZFtUrpjFg01QXZur+TFXxbz9uvlce1z8n0k+Q1AzOm/aZKgvgCoq4spYGqOM0hFWYCeoIVSNtOnhxESqspNgr2+Ycm//dtu/vi77vGHOulHGOlI74xB90wTyk87tYSMzNjM1tbFK+CDPXWcKe5E0yC/IF4+IQSZuc4k6ra4MqoATdqH+jrr22ikpipSkm1sS1F6qgMzeBZfokX+TAPbhsMfxefr/We++axFbZVJ1xDLvM/7JwxwWso2MTuLMDtPYHV/M6hdZZlJOKRITlZMjCQ/toK6OgkCcvJ8AKy8OYNrb8ng7x4r4PKr0gb1k57tKEoJFkUV8NCz9xWBKO3tFVTXxASbMd3R9LEjjSjLEfxbK1ORUnDqeBs1FYF+5COVHCmZmu8IMzAR6mwPc2B3LQC5M30x8iocXfN2T9VgrQEnvnI89Nw5VrTIVFMlCYWc2e/zLRnZHhYumYh7GKc4JdPbN2jUzCKez/49QHFxzBHOm2dh6IrGepOSojCay8+krFQWL08DBXveqqK1qSMy88pZ8+5JrLg+fchE6OUtxZSXdOH2alx1g1PqUraJGRh6zfehvtaissxE02D2LDN6vajEITlzzuAN13Bwe6P80uMUYAm5DVCnyzQCgb7Qopg3zxHs84/DoCUBsPSaSWTl+ujuMtn5Rg2BbhWX3g6XCLkSJLPm+/n+/dNJ8hvDevv+UAoOHXCc7sIFFu7I9qK7W1B2RkNqgnmLU+LavPXSGZ56vJCnHi/k96/EO0LDiFiGIilOAY89t/40sNuy4NhXMfO5ZKFTImtrCfPRu/UAaJpgzZ2pTEzTaGu1+f22TmrrEuMcXlKywZq7ctj4z3Oi1zb+8xzW3JnjkO9n9iPhL5/10FhvkeiDSy+JLadjx52zBDPmJpGYFO9oRVzhMj5DNIzIPUF04GgAVTb/LiRrTxRqYt5FFn6/IsGtuObqMHv2uTh+tIX0LA9zF7owZDvr7vaxd2eAumqTP75RzVU3TuHSpakjEnLGMYcNdf1RWWby+Se9IGDF8lC0UNveLjh50nkHecXVg8PsXX87bdg+g8HomFEvHrXRn23dcBTF7yzLeT/fh5wcm4XznbX34Z5aSk80AOD1adxxbx6XrUgFoTh9aug311m5PrIi4WcsZg9QW22yb3cAZcPiS0ymTYuFvo8PG5gmXHTxBNImu0foZTDaWyNWJKjpuxaXQglL/r3S7ZsrKrWUk6dsx+sCS68wCQQkJaWSd/8Y4OrrvVy8NAchdVasTmfBZam4PUOXjftmxDH70We+6GSYD/cGsSxFfp7N4sUxx1dYqFFVKfH4dJatGlzjaG0KYZqKSelD1zE7WvvyfSf7hQG7wU0v3lOjFA8CfHrY4GxTrP52zTUhpk+3UAo+2h/g0J+aMcPOGkueYAyK7f0xFm9vWYrPDvVwYE8Ay1LMmmmx6rpQdPfX2Cj59IizDlbdmokvcXCtcserZ9j+Qilv/qaMitLBiUh9teNQhbKP9l0bJPV7R3cfv37JbVm2zaLyCkleno07wVFCXp6NsqC+QVJbFaC4sIPkCY7nH5H8KGZfVhJm79sBzpSaIODihSZXLjejL1/b2wX/824CoRAsXDKRxcuH9jVCQkNND20tIeqrg1xyRew521Yc2F2HaSqEbf/sT0ffaYFhDkhs3rzD5W/q/pOCq/1+xZqbQyT3OyBRVSX54CMXwcjrqvRsDxdfnsrMuYkYrphORyLf26so/SbMqa9D1Nc495OSFFddaZKTHXu+vUOwZ4+Ljk5B3sxE1n5vKnKEV1SmqSgpbMef4lSg+1BZ1s0ftpUDnHzk+Q0XRZU2XEe/fOzVJD0g3weWeL2Km28MkZYWU0IoJPjquMbx4zqhiG/RdUHWNB+T0t2kTjbwJ3YjpB1RBrS3WTQ32jQ1WtRUmpiR5e0ynCMyixaZuPpFtcZGyd79jqIzc7zcvn5qnIIBzLBNcWEH+QVJw2aAAPvfruHksTaEUI9veu7efx1VAVElBOVOFNfqOixbGmbu3PjZ7OkRnDwlKSnVaWkZ30HBlGTF7NkWF801cbni7xUWanx6xMCyIG9mIrfcnYNuDHa0J4+1sf/tGtImu7nj3lw8vsG+oaWpl9efK0XZhCyb3Me2bqgfkwIAfv3ArxNCKukZAT8CZ6e4fLkZtySiA7UK6molZ5slzc2Cnh5BOCywbUWCCxITFUl+xZRJNjk5dty5oz60tws+PmxQVemQvfjyVK6+cUq0SjUQPUGLHa+U09TYw/Q5ftb+9eBK07s7qin6uh0FL/7s+Q0/7n9vzFO25YHX7paIrUqRoutw0RyLhQtNfL4xHJQcA7q7BceOa5w8qWOa4PZIVq3N6qvnRVFR2sUH/1PH6nVZ0ZclwW6TA7tryclP5JIrJsY9X17Sxc7tFSgluoQpZm968Z6a/vfHZbNP/OiNyVK3vgBywTkqOz3fYtZMi+zsczwqWyUpLtE4fcZJb4UQzFuUwvLrJuPx6bz10hmEFNF84vjRFt5/p47cGT5uv2f4rA+gq8Nk+wulBAMWCh762fMbnh34zNhOPETw6It/0/jkxm29AFOn2tRUO8IXl2i4XIrMTJuM9Nhhaa87Vm43Q4JAj/PSpbXNOShdV+dsaQGkFMxe4OeyFWnRAgdAbVV87aFgfjIH9zVQeTpAZ3s4uhUeCNNUvLujmmDAAnivK63iuaGeG5cCIpgAcN3KMJYNp4p0ysokTc2C8nKN8vLRmsdjUoabWfP8zF2YMiyZ/khwa1x6xUSO/3no94gA4ZDFrt9W9tUsylQo9N3NmzcPWYgctwIUzkERqSkS3LDokjCLLnHWcHW1pLlF0NIq6ewQ9IaccGkNSAPmLEgmb3YimTle/CmuIcfpgxPL4/3M8tVTWL566ENiPUGLXdsr+yynXtjW9Y+89MOm4foftwIEVAOpgaAg2YgJ5vMpCgqGz/aamyV79zuvuCvKupk1P3lU8jDy7m4gyku6OBCrRFWg5OpNWzeM+BHG2I589cP1S9bdDMzKzbWHDIXDwetVzJhuU1cvaWtVFJ1op6vDZHKGe8R9xFjQ0Rbi4L4GDu2vJ9RrI+CgZXPjo1vXV4zWdvwKuGztVATXJSWq6HEVcE6SDj5LEA+XC2bNshE4WV59TQ/HPm+hrTlE8gQXvqTxGWRjbZBD+xt4/506GmuDAEEUj3dOqvjxPz3x8JhOlo/7k5knHnxlnrS1r1NTbe78juPCj3xmcKJQ45Y1IdKnjOloDm3tgsOHo98FAE4laer0RKZO95EywYXXp+GNKKW70yTQbdLeEqLydDeVp7v7F11tFG9KW/3Lw/9xb9l4+JzTN0NPbtx2HJh/6y1hsjIt/vKlzhdHdZKSFHd+J4TLNfal0doqOfmNpKhIp7d39OcHoE4g3jIFL0TKeuPGOSrgtbtAvJWRbnPbrSFsYNcuFw2NkvR0mxtXh6IFzLHCsqDxrKS+XlLfICiv0Po7/5CCzogDLlWIL4RQhzpTK74YLryNFeekgM2bN8ukptwvgYVXXRlm7hyLzi7Brt0uuroEycmKK1eEyc4aWbbmZslXxzVmzrTIyY49+9VxjcNHDECd6uwSl27+zw095yLnWHBO7vfgwYNq1RW3fCKUvLemWtMyMyyRmqqYOcOmtlbS0iopLtaoq5OYJrgS4t/q1tVLjhwx+PSwTlOzRDdgWm7kVFmd4P2PXKCwEGLtP760YZyp1fhwzvHnwOfvNF5/+W2Nts2tZ8o1JwWeoJhVYGMYzvn+1jZJRaXGiRM6mg4Z6TaWDW++lUBLi0CTsGC+yeVLnBcf1TWSfftcka/GxGOPPLfhvy8k2aFwXgH4vS92f3n95etaLZMbSkp0PB4lpky2yciwmTvHyRM03floMj/fInWiQgowDEV2ts01K8Pk59lICScKNT78yHA+koLNjzy/4YIchx8N5/XpbB+euv+1exBiqwLvlMk2S5aYZGfao/euoLpW8tnnBmfPCoAelNr0yAv3/seFkGssuCAKAPjVA/85XVPqNeBKcI61z5xhMXmyzcRUFS11hULQ0iJobJSUlGq0d0Tf1hwVivs2Pb/h6wsl01hwwRQAoFDimQe2XasUm/p/VzwCbOCILcQvHn1u/bsXUpax4oIqoD9+ufHVTB2xAiFWoEQuqGQEbmVTL4SoENhfSs3a+9CzP2j4/5JhLPg/IsPxlddf0pMAAAAASUVORK5CYII="
    val imageBytes = Base64.decode(value, Base64.DEFAULT)
    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size).asImageBitmap()

    Box(
        modifier = Modifier.size(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            bitmap = decodedImage,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
fun PreferenceIcon(preferenceIcon: PreferenceIcon) {
    Box(
        modifier = Modifier.size(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            bitmap = IconResolver.getBitmap(icon = preferenceIcon),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(30.dp)
        )
    }
}
