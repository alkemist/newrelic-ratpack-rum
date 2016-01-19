import com.newrelic.api.agent.NewRelic

yieldUnescaped '<!DOCTYPE html>'
html {
    head {
        meta charset:'utf-8'
        title "Test Page"
        yieldUnescaped ${NewRelic.browserTimingHeader}
    }
    body {
        p "Hello World! - " + System.currentTimeMillis()
        yieldUnescaped ${NewRelic.browserTimingFooter}
    }
}
