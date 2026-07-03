/**
 * Precompiled [com.useunloq.offers.merchant.gradle.kts][Com_useunloq_offers_merchant_gradle] script plugin.
 *
 * @see Com_useunloq_offers_merchant_gradle
 */
public
class Com_useunloq_offers_merchantPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("Com_useunloq_offers_merchant_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
