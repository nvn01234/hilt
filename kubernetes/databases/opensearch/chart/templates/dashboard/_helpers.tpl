{{/*
Dashboard labels
*/}}
{{- define "dashboards.labels" -}}
{{ include "common.labels" . }}
app.kubernetes.io/component: dashboard
{{- end }}

{{/*
Selector labels
*/}}
{{- define "dashboards.selectorLabels" -}}
{{ include "common.selectorLabels" . }}
app.kubernetes.io/component: dashboard
{{- end }}

{{/*
Auto-scaler
*/}}
{{- define "dashboards.autoScaling" -}}
{{ include ".Values.dashboards.autoscaling" . }}
{{- end }}
