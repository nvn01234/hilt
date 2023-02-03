{{/*
Opensearch labels
*/}}
{{- define "opensearch.labels" -}}
{{ include "common.labels" . }}
app.kubernetes.io/component: opensearch
{{- end }}

{{/*
Selector labels
*/}}
{{- define "opensearch.selectorLabels" -}}
{{ include "common.selectorLabels" . }}
app.kubernetes.io/component: opensearch
{{- end }}
