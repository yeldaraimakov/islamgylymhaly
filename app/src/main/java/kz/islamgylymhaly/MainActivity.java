package kz.islamgylymhaly;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;

    boolean pushedSearch = false;
    private CharSequence mTitle;
    static Field[] fields;
    static int clickedPos, block = 1, blockp = 1, themesSize, allThemePages[];

    static String[] themes, allThemesLower;
    static final String allThemes[] = {"Тазалық және көлемі", "Тазалау жолдары", "Таза саналған нәрселер", "Нәжістер (таза саналмаған нәрселер)",
            "Ауыр ластық (нәжіс)", "Жеңіл ластық (нәжіс)", "Әжетхана әдебі", "Истинжа", "Истибра", "Ғұсыл", "Ғұсыл алынатын жағдайлар",
            "Сүннет және мәндүп ғұсыл", "Ғұсыл парыздары", "Ханафи мәзһабынша ғұсылдың парызы – үшеу", "Ғұсыл сүннеттері",
            "Жүніп адамға харам нәрселер", "Әйелдердің хайыз, нифас және үзір халі (истихада)", "Хайыз", "Нифас", "Әйелдің үзір қаны (итихада)",
            "Дәрет", "Дәреттің парыздары", "Дәреттің сүннеттері", "Ысырап", "Дәрет әдебі", "Дәрет мәкрүһтері", "Дәреттің дұғалары",
            "Дәретті бұзатын нәрселер", "Дәретті бұзбайтын нәрселер", "Дәретке кедергі жасамайтын нәрселер", "Үзірлі адамның дәреті",
            "Мәсіге мәсіх тарту", "Мәсіге мәсіх тарту шарттары", "Мәсіхті бұзатын нәрселер", "Орауыш, таңғыш сыртынан мәсіх тарту",
            "Тәйәммүм алу", "Тәйәммүмнің себептері", "Тәйәммүм сүннеттері", "Тәйәммүм шарттары", "Тәйәммүм уақыты", "Тәйәммүмді бұзатын нәрселер",

            "Намаз және оның дәлелдері", "Намаздың пайдалары мен хикметтері", "Иман", "Адамның негізгі міндеті", "Хақ Тағалаға деген құлшылықтың мәні",
            "Шүкіршілік", "Намаз оқымаған жанның үкімі қандай болмақ?", "Намаз түрлері", "Парыз намаздар", "Уәжіп намаздар", "Нәпіл намаздар", "Намаз уақыты",
            "Бамдат (таң) намазы", "Бесін намазы", "Аср (намаздыгер)", "Ақшам намазы", "Құптан намазы", "Полюстердегі намаз уақыттары", "Мұстахап уақыттар",
            "Мәкрүһ уақыттар", "Нәпіл намаз оқудың мәкрүһ уақыты", "Намаздың бес уақыт болуының хикметтері", "Намаздың парыз болу шарттары",
            "Намаздың сыртқы және ішкі парыздары", "1. Хадасаттан тазалық (Рухани тазалық)", "2. Нәжістерден тазалану (Заттық тазалық)",
            "3. Әуретін жабу", "4. Құбылаға бет бұру", "5. Уақыт", "6. Ниет", "Ішкі парыздары\n1. Ифтитах (бастау, ашу) тәкбірі", "2. Қиям (түрегеп тұру)",
            "3. Қырағат", "4. Рүкүғ", "5. Сәжде", "6. Ақырғы отырыс", "Тағдил әркан", "Намаз уәжіптері", "Намаз сүннеттері", "«Фатиха сүресінен» кейін «әминді» іштей яки жария айту мәселесі",
            "Сүннет әдебі", "Намаз мәкрүһтері", "Намаздағы мәкрүһтер", "Намазды бұзатын нәрселер", "Азан", "Азанның шығу тарихы", "Азанның шарттары",
            "Азаншының ерекшелігі", "Қамат (тәкбір)", "Бес уақыт намаздың оқылуы\nНамазды жеке оқу үлгісі\nБамдат намазы", "Бесін намазы", "Намаздыгер намазы",
            "Ақшам намазы", "Құптан намазы", "Үтір намазы", "Науқас адамдар намаздарын қалай оқиды?", "Орындыққа отырып оқуға да болады", "Жамағатпен намаз", "Жамағат намазда саптың реті",
            "Имамдықтың шарттары", "Намаздың жамағатпен оқылу үлгісі", "Имамға кешігіп ұйыған адам намазын қалай жалғастырады?", "Таң намазының екінші рәкатына үлгерген жағдайда",
            "Ақшам намазының екінші рәкатына үлгерген жағдайда", "Ақшам намазының соңғы рәкатына үлгерген жағдайда", "Төрт рәкатты намаздың соңғы рәкатына үлгерген жағдайда",
            "Төрт рәкатты намаздың үшінші рәкатына үлгерген жағдайда", "Төрт рәкатты намаздың екінші рәкатына үлгерген жағдайда", "Сахаба Муаздың имамдығы",
            "Жұма намазы", "Жұма намазының үкімі – парыз. Парыздығы Құран мен сүннетке сүйенеді", "Жұма намазының парыздығының шарттары",
            "Жұма намазының дұрыс орындалу шарттары", "Хұтпаның уәжіптері", "Хұтпаның сүннеттері", "Жұма намазының сүннеттері", "Айт намаздары",
            "Айт намазының тәкбірлеріне кешігіп қалған жағдайда", "Тәшриқ тәкбірі", "Нәпіл намаздар", "Жүйелі (рауатиб) сүннет намаздар\n1. Мүәккад (бекітілген) сүннеттер",
            "Тарауих намазы неше рәкат?", "2. Мүәккад болмаған (бекітілмеген) мәндүп сүннеттер", "Мәндүп нәпіл намаздар\nТахиятул-мәсжид",
            "Дәрет намазы", "Сәске намазы", "Тәһажжүд намазы", "Истихара намазы", "Тәсбих намазы", "Қажет намазы", "Сапар алдында және сапардан қайтқанда оқылатын намаз",
            "Жауын тілеу намазы", "Кусуф намазы", "Хусуф намазы", "Мүбәрак түндер", "Құран жайлы не білеміз?", "Құран Кәрімнің Аллаһ Тағала тарапынан жіберілгендігінің кейбір дәлелдері",
            "Құран және ғылым", "Өсімдіктер де аналық-аталыққа бөлінеді", "Желдің тозаңдау қызметі", "Аспан әлемі үнемі кеңеюде", "Жердің домалақтығы",
            "Жердің айналу бағыты", "Теңіздердің бір-біріне араласпауы", "Таулардың қозғалуы", "Адам баласының дүниеге келуі", "Шәуеттің бір тамшысы",
            "Шәуеттегі қоспа сұйықтар", "Сәбидің жынысы", "Құрсаққа ілініп тұратын ет", "Сүйектердің бұлшық етпен қапталуы",
            "Биікке көтерілген сайын кеуденің қысылуы", "Жаңбырдағы өлшемділік", "Жолаушылық", "Жолаушылық үкімі қай жерден басталады?",
            "Қателік сәждесі(сәһу сәждесі)", "Қателік сәждесі қалай жасалады?", "Сәһу сәждесін қажет ететін жағдайлар", "Тиләуәт сәждесі", "Шүкір намазы және сәждесі",
            "Шағымдануға еш қақымыз жоқ", "Жаназа намазы", "Өлім тақағанда жасалатын нәрселер", "Жаназаның жуылуы", "Шейіт жайында",
            "Жаназаның кебінделуі", "Жаназа намазы", "Жаназа намазының сүннеті", "Жаназаның апарылуы мен жерленуі", "«Фатиха» сүресі", "«Аятул-Күрсі»",
            "«Ықылас» сүресі", "«Фәлақ» сүресі", "«Нас» сүресі", "Дұғалар\nИфтитах (бастау) тәкбірінен кейін құпия оқылатын «сәна» (мадақ) дұғасы",
            "Рүкүғтан тұрар кезде айтылатын дұға", "Рүкүғтан тұрғаннан кейін құпия айтылатын дұға", "Рүкүғта оқылатын тәсбих", "Сәждеде оқылатын тәсбих",
            "Алғашқы және ақырғы отырыста оқылатын «әт-тахият» дұғасы", "«Әт-тахият» дұғасынан кейін оқылатын салауат дұғасы", "Салауат дұғасынан кейін оқылатын дұға",
            "Үтір намазында оқылатын құныт дұғасы", "Сәлем бергеннен кейін оқылатын дұға", "Азан дұғасы", "Намаз кестесі",

            "Ораза ғибадаты", "Оразаның парыз болуы", "Оразаның пайдалары\n1. Рамазан мен айт мейрамы", "2. Ораза жамандықтан сақтайды",
            "3. Қоғамдық пайдасы", "4. Ораза ырыс-берекенің қадірін білдіреді", "5. Ораза сабырлылық пен төзімділікке үйретеді", "Оразаның түрлері",
            "Парыз ораза", "Уәжіп ораза", "Нәпіл ораза", "Күн ара тұту", "Әр айда үш күн ораза ұстау", "Әр аптаның дүйсенбі, бейсенбі күндері ораза ұстау",
            "Шәууәл айында алты күн ораза ұстау", "Арафа күнінде ораза ұстау", "Мухаррам айының тоғызыншы, оныншы (ашура) және он бірінші күндері ораза ұстау мұстахап немесе сүннет",
            "Харам айларда ораза ұстау", "Шағбан айында ораза ұстау", "Мәкрүһ оразалар", "Оразаның уақыты мен Рамазан айының анықталу жолы\nОразаның уақыты",
            "Рамазан айының анықталуы", "Оразаның шарттары\nОразаның парыз болу шарттары", "Оразаның дұрыс орындалу шарттары", "Ораза ұстағандар үшін мұстахап нәрселер\nОраза ұстағандарға мына нәрселерді жасау – мұстахап",
            "Аузы берік кісіге мәкрүһ және мәкрүһ емес нәрселер", "Ораза бұзылғанда қазасы ғана өтелетін жағдайлар", "Ораза кезінде ине салдыруға бола ма?",
            "Қойшы мен алма ағашы", "Сонымен қатар мына жағдайдағылар бұзылған оразасының тек қана қазасын өтейді", "Әрі қаза, әрі кәффарат қажет еттірген жағдайлар",
            "Оразаны бұзбайтын жағдайлар", "Ораза ұстай алмайтындардың жағдайы", "Кәффарат түрлері", "1. Ораза кәффараты", "2. Қателесіп мұсылманды өлтірудің кәффараты",
            "3. Зиһар кәффараты", "4. Қажылықта ихрам кезінде шаш алдырудың кәффараты", "5. Ант кәффараты", "Ант түрлері\nҒамус анты", "Лағу анты",
            "Мун’ақид анты", "Атау (нәзір)", "Атаудың шарттары", "Атау түрлері", "Иғтиқаф", "Иғтиқафтың шарттары", "Иғтиқаф әдебі", "Иғтиқафты бұзатын нәрселер",

            "Зекет", "Зекеттің пайдалары мен хикметтері", "Зекет ғибадатының үкімі", "Парыздығының дәлелдері", "1. Құран-Кәрім:",
            "2. Сүннет", "3. Ижмағ", "Байлығының зекетін өтемегендердің ақырет күні тартатын азабы", "Зекетті өтемегендердің дүниедегі тартатын жазасы",
            "1. Мұсылман болуы", "2. Нисап көлеміне жететін немесе одан да көп байлыққа ие болу", "3. Зекеті берілетін байлықтың өсімділігі",
            "4. Зекеті берілетін байлыққа бір жыл толуы", "5. Қарыз болмауы", "6. Зекеті берілетін байлыққа толық ие болу", "Зекеттің дұрыс орындалуы үшін қосылған шарттар\n1. Ниет", "2. Иелендіру (тамлик)",
            "Бұл аятта айтылған сегіз топты былай түсіндіруге болады:\n1-2. Пақырлар мен міскіндер", "3. Зекет жинауға тағайындалған арнайы адамдар",
            "4. Муәллафатул-қулууб", "5. Құлдар", "6. Қарызға батқандар", "7. Аллаһ жолында", "8. Қаржылары бітіп, жолда қалғандар", "Қабыл болған садақа",
            "Ақшаның зекеті", "Алтынның зекеті", "Күмістің зекеті", "Төрт-түлік малдың зекеті", "Түйенің зекеті", "Сиырдың зекеті", "Қой мен ешкінің зекеті",
            "Жылқы малының зекеті", "Сауда тауарларының зекеті", "Қорытынды", "Жер қойнауындағы қазба байлықтардың зекеті", "Бау-бақша және егістік өнімдерінің зекеті",
            "Пітір садақа", "Пітір садақаның мөлшері",

            "Қажылық", "Қажылық ғибадатының орындалатын айлары", "Қажылық ғибадатының пайдалары мен хикметтері", "Қағбаның салынуы",
            "Қажылық ғибадатының үкімі", "Қажылықтың парыздығының дәлелдері\n1. Құран", "2. Сүннет", "3. Ижмағ", "Қажылық ғибадатының парыздығының шарттары",
            "а) Денсаулығы болуы", "ә) Қажетті қаражаты болуы", "б) Баратын жолдың сенімді болуы", "Әйелдерге байланысты арнайы шарттар", "Қажылыққа кедергі кейбір жағдайлар\n1. Әке – шеше",
            "2. Ихсар", "Қажылық ғибадатының дұрыс орындалу шарттары", "Қажылықтың парыздары", "Ихрам (қажылықтың бірінші парызы)", "Қажылық түрлері\n1. Ифрад қажылығы",
            "2. Тәматтуғ қажылығы", "3. Қиран қажылығы", "Ихрамның уәжіптері", "Миқат дегеніміз не?", "1. Харам аумағы", "2. Хил аумағы", "3. Әфақ аумағы",
            "Ихрамға кірген адамға тыйылған нәрселер\n1. Ихрамға кірген адамның денесіне байланысты тыйымдар:", "2. Киімге байланысты тыйымдар:", "3. Харам аумағына байланысты тыйымдар:",
            "4. Жалпы тыйымдар:", "Ихрамдағыға рұқсат істер", "Ихрамның сүннет және мұстахаптары", "Арафатта тұру(қажылықтың екінші парызы)", "Арафа күні және Арафатта тұрудың сүннеттері",
            "Зиярат тауабы(қажылықтың үш парызының біреуі)", "Тауап түрлері", "1. Қадум тауабы", "2. Зиярат тауабы", "3. Уадағ (қоштасу) тауабы", "4. Нәпіл тауабы", "5. Умра тауабы",
            "Тауаптың дұрыс орындалуының шарттары", "Тауап уәжіптері", "Тауап сүннеттері", "Қажылықтың уәжіптері", "Ханафи мәзһабында үзір саналатын себептер",
            "Муздәлифәда уақфа жасау (қажылықтың бірінші уәжібі)", "Уақфаның дұрыс орындалуының шарттары", "Ақшам және Құптан намаздарын «жамғу-тахирмен» оқу",
            "Шайтанға тас лақтыру (Қажылықтың екінші уәжібі)", "Айттың алғашқы күні(Зилхижжаның 10-ы)", "Айттың екінші және үшінші күндері(Зилхижжаның 11-і және 12-сі)",
            "Айттың төртінші күні(Зилхижжаның 13-ші)", "Шайтанға тас лақтырудың дұрыс орындалу шарттары", "Шайтанға тас лақтырудағы сүннеттері", "Шайтанға тас лақтырудың мәкрүһтері",
            "Шайтанға тас лақтыруда өкілдік", "Сафа, Мәруа төбелерінің арасында жүгіру(қажылықтың үшінші уәжібі)", "Сафа, Мәруа төбелері және зәмзәм суы",
            "Сағидің дұрыс орындалу шарттары", "Сағи уәжіптері", "Сағи сүннеттері", "Сағи қалай жасалады?", "Шаш алу немесе қысқарту(қажылықтың төртінші уәжібі)",
            "Шашты алғызу мен қысқартудың басқа қажылық амалдары арасындағы орындалу реті", "Қоштасу (уадағ) тауабы(қажылықтың бесінші уәжібі)", "Қоштасу тауабының уәжіптігінің шарттары",
            "Қоштасу тауабын дұрыс орындаудың шарты мен уақыты", "Қажылық сүннеттері", "1. Қадум тауабы", "2. Қажылық хұтпалары", "3. Арафаға қараған түнді Минада өткізу", "4. Құрбан айтқа қараған түнді Муздәлифәда өткізу",
            "5. Айт күндерінде Минада қалу", "Қажылық әдептері", "Қажылық ғибадатының бастан-аяқ толық орындалу барысы\n1. Ихрам", "2. Тауап", "Умра ғибадаты", "Умра ғибадатының үкімі",
            "Умраның парыздары", "Умраның орындалатын уақыты", "Қажылықта және умрада істелетін кемшіліктер және өтелетін жазалар", "1. Қажылық пен умраның қайтадан жасалуын (қаза етілуін) талап ететін кемшіліктер",
            "2. Түйе немесе сиырдың жаза ретінде шалынуын талап ететін кемшіліктер", "3. Қой немесе ешкінің жаза ретінде шалынуын талап ететін кемшіліктер екіге бөлінеді:",
            "4. Пітір садақасы мөлшерінде садақа беруді талап ететін кемшіліктер", "5. Пітір садақасының мөлшерінен аз мөлшерде садақа беруді талап ететін кемшіліктер",
            "6. Құнының өтелуін талап ететін кемшіліктер", "7. Харам аумағының тыйымдарына байланысты кемшіліктер", "Һәди(қажылықта және умрада шалынатын құрбандар)",
            "Һәди құрбандары екіге бөлінеді: уәжіп және нәпі\nа) Нәпіл құрбан", "б) Уәжіп құрбан", "Һәди құрбанының шалынатын орны мен уақыты", "Өкілдік", "Өкілдік шарттары",
            "Қасиетті Мәдина қаласындағы Хазіреті Пайғамбарымыздың (с.а.у.) қабірін зиярат ету", "Қажылықта оқылатын сүннет дұғалар және олардың оқылатын орындары\nТәлбия дұғасы",
            "Тауаптың барлық айналымдарында", "Сафа мен Мәруада", "Шайтан тасқа әрбір тасты лақтырған уақытта оқылатын дұға", "Тәшриқ тәкбірі",

            "Құрбанның танымы және Ислам дініндегі орны", "Құрбан шалудың пайдалары мен хикметтері", "Құрбан айтта құрбан шалудың үкімі",
             "Құрбан шалудың уақыты", "Құрбандыққа шалуға болатын малдар", "Бірігіп құрбан шалу", "Құрбандыққа жарамайтын малдар",
            "Құрбан шалуда өкілдік", "Құрбандыққа шалынған малдың еті мен терісі", "Нәзір құрбаны",

            "Аңшылық", "Ауланған аңның етінің халал болуы үшін керекті шарттар", "Ислам дініндегі еті желінетін және еті желінбейтін хайуандар",
            "1. Тек қана суда өмір сүретін хайуандар", "2. Суда да, құрлықта да өмір сүретін хайуандар", "3. Тек қана құрлықта өмір сүретін хайуандар",
            "Еті желінетін және еті желінбейтін, тек қана құрлықта өмір сүретін хайуандар үшке бөлінеді", "Халал малдардың кейбір жағдайларға байланысты харамдануы"
    };
    char notNeedChars[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', ',', '?', ':', '(', ')', '-', '«', '»'};
    final static int[] blockPage = {0, 41, 187, 236, 275, 376, 386, 394};

    public static class MyFragment extends Fragment {
        ListView listView;

        void getThemes() {
            if (block != -1) {
                themesSize = 0;
                allThemePages = new int[blockPage[7] + 1];
                for (int i = blockPage[block - 1]; i < blockPage[block]; ++i) allThemePages[themesSize++] = i;
            }
            themes = new String[themesSize];
            for (int i=0; i<themesSize; ++i) themes[i] = allThemes[allThemePages[i]];
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            getThemes();

            View view = inflater.inflate(R.layout.myfragment, null);
            listView = (ListView) view.findViewById(R.id.listView);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item2, R.id.label2, themes);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    clickedPos = position;
                    Intent intent = new Intent(getActivity(), PageActivity.class);
                    startActivity(intent);
                }
            });

            return view;
        }
    }

    // delete not need chars from themes (like ".", ",", ...)
    String deleteNotNeedChars(String s) {
        String ans = "";
        for (int j = 0; j < s.length(); ++j) {
            boolean ok = false;
            for (int u = 0; u < notNeedChars.length; ++u)
                if (s.charAt(j) == notNeedChars[u]) ok = true;
            if (!ok) ans += s.charAt(j);
        }
        return ans;
    }

    // to get all themes in String[] allThemes (lower case)
    void getAllThemes() {
        int k = 0;
        allThemesLower = new String[allThemes.length];
        for (int i=0; i<allThemes.length; ++i) allThemesLower[k++] = deleteNotNeedChars(allThemes[i].toLowerCase());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAllThemes();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getString(R.string.title_section1);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        // to get all files in raw
        fields = R.raw.class.getFields();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        block = position + 1;
        switch (block) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
            case 6:
                mTitle = getString(R.string.title_section6);
                break;
            case 7:
                mTitle = getString(R.string.title_section7);
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new MyFragment()).commit();
    }

    public void onSectionAttached(int number) {
        Log.d("Now ", "in onsectionattached");
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
        actionBar.setDisplayShowCustomEnabled(false);
        NavigationDrawerFragment.mDrawerToggle.setDrawerIndicatorEnabled(true);
    }

    private void showExtraActionBar() {
        NavigationDrawerFragment.mDrawerToggle.setDrawerIndicatorEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(R.layout.search);
        EditText editText = (EditText) actionBar.getCustomView().findViewById(R.id.editInput);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchByKey(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP|ActionBar.DISPLAY_SHOW_CUSTOM);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            if (pushedSearch) {
                menu.removeItem(R.id.search_bar);
                showExtraActionBar();
            } else {
                restoreActionBar();
            }
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search_bar) {
            blockp = block;
            pushedSearch = true;
        }
        if (id == android.R.id.home && pushedSearch) {
            searchByKey("");
            pushedSearch = false;
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
        if (id == R.id.extra0) {
            Intent intent = new Intent(MainActivity.this, Extra0.class);
            startActivity(intent);
        }
        if (id == R.id.extra1) {
            Intent intent = new Intent(MainActivity.this, Extra1.class);
            startActivity(intent);
        }
        if (id == R.id.extra2) {
            Intent intent = new Intent(MainActivity.this, Extra2.class);
            startActivity(intent);
        }
        invalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    public void searchByKey(String key) {
        if (key.length() == 0) {
            block = blockp;
        } else {
            key = changeKey(key);
            allThemePages = new int[allThemes.length];

            block = -1;
            themesSize = 0;

            int len = key.length();
            for (int i = 0; i < allThemesLower.length; ++i)
                for (int j = 0; j < allThemesLower[i].length(); ++j)
                    if ((j == 0 || allThemesLower[i].charAt(j - 1) == ' ') && j + len <= allThemesLower[i].length() && allThemesLower[i].substring(j, j + len).equals(key)) {
                        allThemePages[themesSize++] = i;
                        break;
                    }
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new MyFragment()).commit();
    }

    private String changeKey(String key) {
        String newKey = "";
        for (int j = 0; j < key.length(); ++j) {
            if ((int) key.charAt(j) == 601) newKey += 'ә';
            else newKey += key.charAt(j);
        }
        return newKey;
    }
}
